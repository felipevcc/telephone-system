package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;

public interface TelephoneNumberService {

    void runNumberTrackingProcess();

    TelephoneNumber releaseTelephoneNumber(Integer phoneNumber);

    TelephoneNumber getTelephoneNumberByCustomer(Long customerId);
}
