package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.CenterDTO;
import com.segurosbolivar.telephonenumberservice.dto.CustomerDTO;
import com.segurosbolivar.telephonenumberservice.dto.TelephoneNumberAssignmentDTO;
import com.segurosbolivar.telephonenumberservice.dto.TelephoneNumberDTO;
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
        System.out.println(foundCenters);

        List<CenterDTO> centers = new ArrayList<>(foundCenters);

        TelephoneNumberDTO assignedTelephoneNumber = null;

        for (CenterDTO center : centers) {
            System.out.println(centers);
            Integer centerRangeSize = center.getFinalNumber() - center.getInitialNumber() + 1;

            // Validate if there is availability in the center
            Integer occupiedSize = telNumberRepository.countNumbersAssignedByCenter(center.getCenterId()) +
                    telNumberAuditRepository.countActiveNumbersByCenter(center.getCenterId());
            Integer centerFreeSize = centerRangeSize - occupiedSize;
            if (centerFreeSize < 1) {
                centers.remove(center);
                continue;
            }

            if (customer.getCustomerTypeId() == 2) {
                assignedTelephoneNumber = assignBusinessNumber(centerFreeSize, center, customer);
            }
            if (assignedTelephoneNumber != null) {
                break;
            }
            System.out.println(centerRangeSize);
            System.out.println(occupiedSize);
            System.out.println(centerFreeSize);
        }
        if (assignedTelephoneNumber == null && centers.isEmpty()) {
            throw new NoCentersAvailableException("There are no centers with availability in the customer's area");
        } else if (assignedTelephoneNumber == null) {
            System.out.println("elseif de solo null");
            throw new RuntimeException();
        }

        return assignedTelephoneNumber;
    }

    public TelephoneNumberDTO assignBusinessNumber(Integer centerFreeSize, CenterDTO center, CustomerDTO customer) {
        int rangeSizeToSearch = 100;
        int totalNumberPages = (int) Math.ceil((double) centerFreeSize / rangeSizeToSearch);
        int pageCounter = 1;
        Integer initialNumber = center.getInitialNumber();
        Integer finalNumber = center.getFinalNumber();
        TelephoneNumberDTO assignedTelephoneNumber = null;

        for (int startOfRange = initialNumber; startOfRange <= finalNumber; startOfRange += rangeSizeToSearch) {
            int endOfRange = Math.min(startOfRange + rangeSizeToSearch - 1, finalNumber);
            if (endOfRange == finalNumber) {
                rangeSizeToSearch = endOfRange - startOfRange + 1;
            }
            List<Integer> numbers = telNumberRepository.findAvailableNumbersByRange(startOfRange, rangeSizeToSearch);
            for (Integer number : numbers) {
                // Commercial number validation
                if (NumberHelper.isCommercial(String.valueOf(number))) {
                    TelephoneNumberAssignmentDTO assignment = new TelephoneNumberAssignmentDTO();
                    assignment.setCenterId(center.getCenterId());
                    assignment.setCustomerId(customer.getCustomerId());
                    assignment.setPhoneNumber(number);
                    Long numberRecordId = telNumberCallRepository.assignTelephoneNumber(assignment);
                    TelephoneNumber newAssignedNumber = telNumberRepository.findTelephoneNumberById(numberRecordId);
                    assignedTelephoneNumber = telNumberMapper.telNumberToDTO(newAssignedNumber);
                    break;
                }
            }
            if (assignedTelephoneNumber != null) {
                break;
            } else if (pageCounter == totalNumberPages) {
                break;
            }
            pageCounter++;
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
