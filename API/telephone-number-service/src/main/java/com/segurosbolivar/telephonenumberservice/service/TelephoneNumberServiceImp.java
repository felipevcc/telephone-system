package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberAuditRepository;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberCallRepository;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelephoneNumberServiceImp implements TelephoneNumberService {

    @Autowired
    TelephoneNumberCallRepository telNumberCallRepository;

    @Autowired
    TelephoneNumberRepository telNumberRepository;

    @Autowired
    TelephoneNumberAuditRepository telNumberAuditRepository;

    @Override
    public void runNumberTrackingProcess() {
        telNumberCallRepository.runNumberTrackingProcess();
    }

    @Override
    public TelephoneNumber releaseTelephoneNumber(Integer phoneNumber) {
        try {
            TelephoneNumber telephoneNumber = telNumberRepository.findTelephoneNumber(phoneNumber);

            if (telephoneNumber == null || telephoneNumber.getReleaseDate() != null) {
                return null;
            }
            telNumberCallRepository.releaseTelephoneNumber(phoneNumber);
            return telNumberRepository.findTelephoneNumber(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TelephoneNumber getTelephoneNumberByCustomer(Long customerId) {
        try {
            return telNumberRepository.findTelephoneNumberByCustomer(customerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
