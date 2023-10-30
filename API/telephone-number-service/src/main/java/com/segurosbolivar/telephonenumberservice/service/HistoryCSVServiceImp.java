package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.CustomerDTO;
import com.segurosbolivar.telephonenumberservice.dto.NumberHistoryRowDTO;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberRepository;
import com.segurosbolivar.telephonenumberservice.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class HistoryCSVServiceImp implements HistoryCSVService {

    @Autowired
    RestClientService restClientService;

    @Autowired
    TelephoneNumberRepository telNumberRepository;

    @Override
    public ByteArrayInputStream loadCustomerHistoryCSV(Long customerId) {
        List<NumberHistoryRowDTO> telephoneNumberHistory = new ArrayList<>();

        List<TelephoneNumber> telephoneNumbers = telNumberRepository.findNumberHistoryByCustomer(customerId);
        TelephoneNumber assignedTelephoneNumber = telNumberRepository.findTelephoneNumberByCustomer(customerId);

        for (TelephoneNumber telNumber : telephoneNumbers) {
            telephoneNumberHistory.add(generateHistoryRow(telNumber));
        }

        // Check if the customer currently has a number assigned and add it to history
        if (assignedTelephoneNumber != null) {
            telephoneNumberHistory.add(generateHistoryRow(assignedTelephoneNumber));
        }

        return CSVHelper.historyToCSV(telephoneNumberHistory);
    }

    @Override
    public ByteArrayInputStream loadNumberHistoryCSV(Integer phoneNumber) {
        List<NumberHistoryRowDTO> telephoneNumberHistory = new ArrayList<>();

        List<TelephoneNumber> telephoneNumbers = telNumberRepository.findTelephoneNumberHistory(phoneNumber);
        TelephoneNumber activeTelephoneNumber = telNumberRepository.findTelephoneNumber(phoneNumber);

        for (TelephoneNumber telNumber : telephoneNumbers) {
            telephoneNumberHistory.add(generateHistoryRow(telNumber));
        }

        // Check if the number is currently active and add it to history
        if (activeTelephoneNumber != null) {
            telephoneNumberHistory.add(generateHistoryRow(activeTelephoneNumber));
        }

        return CSVHelper.historyToCSV(telephoneNumberHistory);
    }

    public NumberHistoryRowDTO generateHistoryRow(TelephoneNumber telephoneNumber) {
        NumberHistoryRowDTO row = new NumberHistoryRowDTO();
        CustomerDTO customer = restClientService.getCustomerById(telephoneNumber.getCustomerId());
        row.setCustomerDocumentType(restClientService.getDocumentTypeCode(customer.getDocumentTypeId()));
        row.setCustomerDocument(customer.getDocument());
        row.setTelephoneNumber(telephoneNumber.getPhoneNumber());
        row.setCenterId(telephoneNumber.getCenterId());
        row.setAssignmentDate(String.valueOf(telephoneNumber.getAssignmentDate()));
        row.setReleaseDate(String.valueOf(telephoneNumber.getReleaseDate()));
        return row;
    }
}
