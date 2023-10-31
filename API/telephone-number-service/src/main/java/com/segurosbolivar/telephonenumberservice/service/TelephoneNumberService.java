package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.TelephoneNumberDTO;

public interface TelephoneNumberService {

    TelephoneNumberDTO getTelephoneNumber(Integer phoneNumber);

    TelephoneNumberDTO getTelephoneNumberByCustomer(Long customerId);

    void runNumberTrackingProcess();

    TelephoneNumberDTO assignTelephoneNumber(Long customerId);

    TelephoneNumberDTO releaseTelephoneNumber(Integer phoneNumber);
}
