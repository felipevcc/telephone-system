package com.segurosbolivar.customerservice.service;

import com.segurosbolivar.customerservice.dto.CustomerDTO;

public interface CustomerService {
    CustomerDTO getCustomerByDocument(Long documentTypeId, String document);
}
