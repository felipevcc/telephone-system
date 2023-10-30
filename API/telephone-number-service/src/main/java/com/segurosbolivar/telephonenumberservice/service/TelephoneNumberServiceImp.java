package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.CenterDTO;
import com.segurosbolivar.telephonenumberservice.dto.CustomerDTO;
import com.segurosbolivar.telephonenumberservice.exceptions.AlreadyHasAssignedNumberException;
import com.segurosbolivar.telephonenumberservice.exceptions.NoCentersAvailableException;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
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
    TelephoneNumberCallRepository telNumberCallRepository;

    @Autowired
    TelephoneNumberRepository telNumberRepository;

    @Autowired
    TelephoneNumberAuditRepository telNumberAuditRepository;

    @Override
    public TelephoneNumber getTelephoneNumber(Integer phoneNumber) {
        TelephoneNumber activeTelephoneNumber = telNumberRepository.findTelephoneNumber(phoneNumber);
        // Check if the number is currently active
        if (activeTelephoneNumber != null) {
            return activeTelephoneNumber;
        }
        // Most recent record in the telephone number history
        return telNumberRepository.findLatestTelephoneNumberRelease(phoneNumber);
    }

    @Override
    public TelephoneNumber getTelephoneNumberByCustomer(Long customerId) {
        return telNumberRepository.findTelephoneNumberByCustomer(customerId);
    }

    @Override
    public void runNumberTrackingProcess() {
        telNumberCallRepository.runNumberTrackingProcess();
    }

    @Override
    public TelephoneNumber assignTelephoneNumber(Long customerId) {
        if (telNumberRepository.findTelephoneNumberByCustomer(customerId) != null) {
            throw new AlreadyHasAssignedNumberException("There are no centers with availability in the customer's area");
        }
        CustomerDTO customer = restClientService.getCustomerById(customerId);
        List<CenterDTO> centers = restClientService.getAllCentersByArea(customer.getAreaId());

        if (centers.isEmpty()) {
            throw new NoCentersAvailableException("There are no centers with attention in the customer's area");
        }

        return null;
    }

    @Override
    public TelephoneNumber releaseTelephoneNumber(Integer phoneNumber) {
        TelephoneNumber telephoneNumber = telNumberRepository.findTelephoneNumber(phoneNumber);
        if (telephoneNumber == null || telephoneNumber.getReleaseDate() != null) {
            return null;
        }
        telNumberCallRepository.releaseTelephoneNumber(phoneNumber);
        return telNumberRepository.findTelephoneNumber(phoneNumber);
    }
}
