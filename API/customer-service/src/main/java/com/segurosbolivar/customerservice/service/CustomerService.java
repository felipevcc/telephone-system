package com.segurosbolivar.customerservice.service;

import com.segurosbolivar.customerservice.dto.CustomerCreationDTO;
import com.segurosbolivar.customerservice.dto.CustomerUpdateDTO;
import com.segurosbolivar.customerservice.model.Customer;
import com.segurosbolivar.customerservice.model.CustomerType;
import com.segurosbolivar.customerservice.model.DocumentType;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(CustomerCreationDTO newCustomerData);

    Customer updateCustomer(Long customerId, CustomerUpdateDTO customerData);

    Customer getCustomerByDocument(Long documentTypeId, String document);

    List<CustomerType> getCustomerTypes();

    List<DocumentType> getDocumentTypes();
}
