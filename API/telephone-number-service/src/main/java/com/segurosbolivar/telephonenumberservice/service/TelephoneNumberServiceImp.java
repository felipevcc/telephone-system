package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.CenterDTO;
import com.segurosbolivar.telephonenumberservice.dto.CustomerDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<CenterDTO> centers = restClientService.getAllCentersByArea(customer.getAreaId());
        if (centers.isEmpty()) {
            throw new NoCentersAvailableException("There are no centers with attention in the customer's area");
        }

        return null;
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
