package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.model.MinimumTimeSetting;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;

import java.io.ByteArrayInputStream;

public interface TelephoneNumberService {

    TelephoneNumber getTelephoneNumber(Integer phoneNumber);

    TelephoneNumber getTelephoneNumberByCustomer(Long customerId);

    void runNumberTrackingProcess();

    TelephoneNumber assignTelephoneNumber(Long customerId);

    TelephoneNumber releaseTelephoneNumber(Integer phoneNumber);

    ByteArrayInputStream loadCustomerHistoryCSV(Long customerId);

    ByteArrayInputStream loadNumberHistoryCSV(Integer phoneNumber);

    MinimumTimeSetting getTimeSetting();

    MinimumTimeSetting createTimeSetting(Integer timeValue);
}
