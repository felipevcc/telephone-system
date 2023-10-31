package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.CustomerDTO;
import com.segurosbolivar.telephonenumberservice.dto.NumberHistoryRowDTO;
import com.segurosbolivar.telephonenumberservice.dto.TelephoneNumberDTO;
import com.segurosbolivar.telephonenumberservice.mapper.TelephoneNumberMapper;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumberAudit;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberAuditRepository;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberRepository;
import com.segurosbolivar.telephonenumberservice.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryCSVServiceImp implements HistoryCSVService {

    @Autowired
    RestClientService restClientService;

    @Autowired
    TelephoneNumberMapper telNumberMapper;

    @Autowired
    TelephoneNumberRepository telNumberRepository;

    @Autowired
    TelephoneNumberAuditRepository telNumberAuditRepository;

    @Override
    public ByteArrayInputStream loadCustomerHistoryCSV(Long customerId) {
        List<NumberHistoryRowDTO> telephoneNumberHistory = new ArrayList<>();

        List<TelephoneNumberAudit> telephoneNumbers = telNumberAuditRepository.findNumberHistoryByCustomer(customerId);
        TelephoneNumber assignedTelephoneNumber = telNumberRepository.findTelephoneNumberByCustomer(customerId);

        for (TelephoneNumberAudit telNumber : telephoneNumbers) {
            telephoneNumberHistory.add(generateHistoryRow(telNumberMapper.telNumberAuditToDTO(telNumber)));
        }

        // Check if the customer currently has a number assigned and add it to history
        if (assignedTelephoneNumber != null) {
            telephoneNumberHistory.add(generateHistoryRow(telNumberMapper.telNumberToDTO(assignedTelephoneNumber)));
        }

        return CSVHelper.historyToCSV(telephoneNumberHistory);
    }

    @Override
    public ByteArrayInputStream loadNumberHistoryCSV(Integer phoneNumber) {
        List<NumberHistoryRowDTO> telephoneNumberHistory = new ArrayList<>();

        List<TelephoneNumberAudit> telephoneNumbers = telNumberAuditRepository.findTelephoneNumberHistory(phoneNumber);
        TelephoneNumber activeTelephoneNumber = telNumberRepository.findTelephoneNumber(phoneNumber);

        for (TelephoneNumberAudit telNumber : telephoneNumbers) {
            telephoneNumberHistory.add(generateHistoryRow(telNumberMapper.telNumberAuditToDTO(telNumber)));
        }

        // Check if the number is currently active and add it to history
        if (activeTelephoneNumber != null) {
            telephoneNumberHistory.add(generateHistoryRow(telNumberMapper.telNumberToDTO(activeTelephoneNumber)));
        }

        return CSVHelper.historyToCSV(telephoneNumberHistory);
    }

    public NumberHistoryRowDTO generateHistoryRow(TelephoneNumberDTO telephoneNumber) {
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
