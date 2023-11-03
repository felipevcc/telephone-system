package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.*;
import com.segurosbolivar.telephonenumberservice.exceptions.AlreadyHasAssignedNumberException;
import com.segurosbolivar.telephonenumberservice.exceptions.NoCentersAvailableException;
import com.segurosbolivar.telephonenumberservice.exceptions.NoClientExistsException;
import com.segurosbolivar.telephonenumberservice.mapper.TelephoneNumberMapper;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumberAudit;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberAuditRepository;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberCallRepository;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberRepository;
import com.segurosbolivar.telephonenumberservice.util.NumberHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TelephoneNumberServiceImp implements TelephoneNumberService {

    @Autowired
    RestClientService restClientService;

    @Autowired
    TelephoneNumberMapper telNumberMapper;

    @Autowired
    TelephoneNumberCallRepository telNumberCallRepository;

    @Autowired
    TelephoneNumberRepository telNumberRepository;

    @Autowired
    TelephoneNumberAuditRepository telNumberAuditRepository;

    @Override
    public TelephoneNumberDTO getTelephoneNumber(Integer phoneNumber) {
        TelephoneNumber activeTelephoneNumber = telNumberRepository.findTelephoneNumber(phoneNumber);
        // Check if the number is currently active
        if (activeTelephoneNumber != null) {
            return telNumberMapper.telNumberToDTO(activeTelephoneNumber);
        }
        // Most recent record in the telephone number history
        TelephoneNumberAudit latestTelNumber = telNumberAuditRepository.findLatestTelephoneNumberRelease(phoneNumber);
        if (latestTelNumber == null) {
            return null;
        }
        return telNumberMapper.telNumberAuditToDTO(latestTelNumber);
    }

    @Override
    public TelephoneNumberDTO getTelephoneNumberByCustomer(Long customerId) {
        TelephoneNumber telNumber = telNumberRepository.findTelephoneNumberByCustomer(customerId);
        if (telNumber == null) {
            return null;
        }
        return telNumberMapper.telNumberToDTO(telNumber);
    }

    @Override
    public void runNumberTrackingProcess() {
        telNumberCallRepository.runNumberTrackingProcess();
    }

    @Override
    public TelephoneNumberDTO assignTelephoneNumber(Long customerId) {
        CustomerDTO customer = restClientService.getCustomerById(customerId);
        if (customer == null) {
            throw new NoClientExistsException("Customer does not exist");
        } else if (telNumberRepository.findTelephoneNumberByCustomer(customerId) != null) {
            throw new AlreadyHasAssignedNumberException("The customer already has a telephone number assigned");
        }

        List<CenterDTO> foundCenters = restClientService.getAllCentersByArea(customer.getAreaId());
        if (foundCenters.isEmpty()) {
            throw new NoCentersAvailableException("There are no centers with attention in the customer's area");
        }
        Collections.shuffle(foundCenters);

        List<CenterDTO> centers = new ArrayList<>(foundCenters);

        TelephoneNumberDTO assignedTelephoneNumber = null;

        for (CenterDTO center : centers) {
            Integer centerRangeSize = center.getFinalNumber() - center.getInitialNumber() + 1;

            // Validate if there is availability in the center
            Integer occupiedSize = telNumberRepository.countNumbersAssignedByCenter(center.getCenterId()) +
                    telNumberAuditRepository.countActiveNumbersByCenter(center.getCenterId());
            Integer centerFreeSize = centerRangeSize - occupiedSize;
            if (centerFreeSize < 1) {
                centers.remove(center);
                continue;
            }
            if (customer.getCustomerTypeId() == 1) {
                assignedTelephoneNumber = assignResidentialNumber(centerFreeSize, center, customer);
            } else if (customer.getCustomerTypeId() == 2) {
                assignedTelephoneNumber = assignBusinessNumber(centerFreeSize, center, customer);
            }

            if (assignedTelephoneNumber != null) {
                break;
            }
        }
        if (assignedTelephoneNumber == null && centers.isEmpty()) {
            throw new NoCentersAvailableException("There are no centers with availability in the customer's area");
        } else if (assignedTelephoneNumber == null) {
            throw new RuntimeException();
        }

        return assignedTelephoneNumber;
    }

    public TelephoneNumberDTO assignResidentialNumber(Integer centerFreeSize, CenterDTO center, CustomerDTO customer) {
        int rangeSizeToSearch = 100;
        int numberCounter = 1;
        TelephoneNumberDTO assignedTelephoneNumber = null;
        String fullName = customer.getName() + customer.getLastName();
        String nameNumber = NumberHelper.mapNameToNumbers(fullName);

        List<RangeDTO> rangesByName = NumberHelper.rangesByNameNumber(nameNumber, center);

        for (RangeDTO range : rangesByName) {

            for (int startOfPage = range.getInitialNumber(); startOfPage <= range.getFinalNumber() && numberCounter <= centerFreeSize; startOfPage += rangeSizeToSearch) {
                int endOfPage = Math.min(startOfPage + rangeSizeToSearch - 1, range.getFinalNumber());
                if (endOfPage == range.getFinalNumber()) {
                    rangeSizeToSearch = endOfPage - startOfPage + 1;
                }
                List<Integer> numbers = telNumberRepository.findAvailableNumbersByRange(startOfPage, rangeSizeToSearch);
                for (Integer number : numbers) {
                    // Validate iteration counter with number of available numbers
                    if (numberCounter > centerFreeSize) {
                        break;
                    }

                    TelephoneNumberAssignmentDTO assignment = new TelephoneNumberAssignmentDTO();
                    assignment.setCenterId(center.getCenterId());
                    assignment.setCustomerId(customer.getCustomerId());
                    assignment.setPhoneNumber(number);
                    Long numberRecordId = telNumberCallRepository.assignTelephoneNumber(assignment);
                    if (numberRecordId == null) {
                        numberCounter++;
                        continue;
                    }
                    TelephoneNumber newAssignedNumber = telNumberRepository.findTelephoneNumberById(numberRecordId);
                    assignedTelephoneNumber = telNumberMapper.telNumberToDTO(newAssignedNumber);
                    return assignedTelephoneNumber;
                }
            }
        }
        return null;
    }

    public TelephoneNumberDTO assignBusinessNumber(Integer centerFreeSize, CenterDTO center, CustomerDTO customer) {
        int rangeSizeToSearch = 100;
        int numberCounter = 1;
        Integer initialNumber = center.getInitialNumber();
        Integer finalNumber = center.getFinalNumber();
        TelephoneNumberDTO assignedTelephoneNumber = null;

        for (int startOfRange = initialNumber; startOfRange <= finalNumber && numberCounter <= centerFreeSize; startOfRange += rangeSizeToSearch) {
            int endOfRange = Math.min(startOfRange + rangeSizeToSearch - 1, finalNumber);
            if (endOfRange == finalNumber) {
                rangeSizeToSearch = endOfRange - startOfRange + 1;
            }
            List<Integer> numbers = telNumberRepository.findAvailableNumbersByRange(startOfRange, rangeSizeToSearch);
            for (Integer number : numbers) {
                // Validate iteration counter with number of available numbers
                if (numberCounter > centerFreeSize) {
                    break;
                }
                // Commercial number validation
                if (NumberHelper.isCommercial(number)) {
                    TelephoneNumberAssignmentDTO assignment = new TelephoneNumberAssignmentDTO();
                    assignment.setCenterId(center.getCenterId());
                    assignment.setCustomerId(customer.getCustomerId());
                    assignment.setPhoneNumber(number);
                    Long numberRecordId = telNumberCallRepository.assignTelephoneNumber(assignment);
                    if (numberRecordId == null) {
                        numberCounter++;
                        continue;
                    }
                    TelephoneNumber newAssignedNumber = telNumberRepository.findTelephoneNumberById(numberRecordId);
                    assignedTelephoneNumber = telNumberMapper.telNumberToDTO(newAssignedNumber);
                    break;
                }
                numberCounter++;
            }
            if (assignedTelephoneNumber != null) {
                break;
            }
        }
        return assignedTelephoneNumber;
    }

    @Override
    public TelephoneNumberDTO releaseTelephoneNumber(Integer phoneNumber) {
        TelephoneNumber telephoneNumber = telNumberRepository.findTelephoneNumber(phoneNumber);
        if (telephoneNumber == null) {
            return null;
        }
        telNumberCallRepository.releaseTelephoneNumber(phoneNumber);
        TelephoneNumberAudit releasedTelNumber = telNumberAuditRepository.findReleasedTelephoneNumber(phoneNumber);
        if (releasedTelNumber == null) {
            return null;
        }
        return telNumberMapper.telNumberAuditToDTO(releasedTelNumber);
    }
}
