package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.CenterDTO;
import com.segurosbolivar.telephonenumberservice.dto.CustomerDTO;
import com.segurosbolivar.telephonenumberservice.dto.NumberHistoryRowDTO;
import com.segurosbolivar.telephonenumberservice.exceptions.AlreadyHasAssignedNumberException;
import com.segurosbolivar.telephonenumberservice.exceptions.NoCentersAvailableException;
import com.segurosbolivar.telephonenumberservice.model.MinimumTimeSetting;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberAuditRepository;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberCallRepository;
import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberRepository;
import com.segurosbolivar.telephonenumberservice.repository.TimeSettingRepository;
import com.segurosbolivar.telephonenumberservice.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
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

    @Autowired
    TimeSettingRepository timeSettingRepository;

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

    @Override
    public MinimumTimeSetting getTimeSetting() {
        return timeSettingRepository.getLatestTimeSetting();
    }

    @Override
    public MinimumTimeSetting createTimeSetting(Integer timeValue) {
        timeSettingRepository.createTimeValue(timeValue);
        return timeSettingRepository.getLatestTimeSetting();
    }

}
