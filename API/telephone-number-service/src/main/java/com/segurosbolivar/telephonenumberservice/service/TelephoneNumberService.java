package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;

import java.io.ByteArrayInputStream;

public interface TelephoneNumberService {

    void runNumberTrackingProcess();

    TelephoneNumber releaseTelephoneNumber(Integer phoneNumber);

    TelephoneNumber getTelephoneNumberByCustomer(Long customerId);

    ByteArrayInputStream loadCustomerHistoryCSV(Long customerId);

    ByteArrayInputStream loadNumberHistoryCSV(Integer telephoneNumber);
}
