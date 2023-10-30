package com.segurosbolivar.telephonenumberservice.service;

import java.io.ByteArrayInputStream;

public interface HistoryCSVService {

    ByteArrayInputStream loadCustomerHistoryCSV(Long customerId);

    ByteArrayInputStream loadNumberHistoryCSV(Integer phoneNumber);
}
