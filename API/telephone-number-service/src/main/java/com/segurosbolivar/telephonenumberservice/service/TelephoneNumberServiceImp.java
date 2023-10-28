package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.NumberHistoryRowDTO;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberAuditRepository;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberCallRepository;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberRepository;
import com.segurosbolivar.telephonenumberservice.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
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
        TelephoneNumber telephoneNumber = telNumberRepository.findTelephoneNumber(phoneNumber);
        if (telephoneNumber == null || telephoneNumber.getReleaseDate() != null) {
            return null;
        }
        telNumberCallRepository.releaseTelephoneNumber(phoneNumber);
        return telNumberRepository.findTelephoneNumber(phoneNumber);
    }

    @Override
    public TelephoneNumber getTelephoneNumberByCustomer(Long customerId) {
        return telNumberRepository.findTelephoneNumberByCustomer(customerId);
    }

    @Override
    public ByteArrayInputStream loadCustomerHistoryCSV(Long customerId) {
        List<NumberHistoryRowDTO> telephoneNumberHistory = new ArrayList<>();
        TelephoneNumber assignedTelephoneNumber = telNumberRepository.findTelephoneNumberByCustomer(customerId);
        List<TelephoneNumber> telephoneNumbers = telNumberRepository.findNumberHistoryByCustomer(customerId);
        if (assignedTelephoneNumber != null) {

        }

        return CSVHelper.historyToCSV(telephoneNumberHistory);
    }

    @Override
    public ByteArrayInputStream loadNumberHistoryCSV(Integer telephoneNumber) {
        List<NumberHistoryRowDTO> telephoneNumberHistory = new ArrayList<>();
        TelephoneNumber activeTelephoneNumber = telNumberRepository.findTelephoneNumber(telephoneNumber);
        List<TelephoneNumber> telephoneNumbers = telNumberRepository.findTelephoneNumberHistory(telephoneNumber);

        return CSVHelper.historyToCSV(telephoneNumberHistory);
    }
}
