package com.segurosbolivar.customerservice.service;

import com.segurosbolivar.customerservice.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerCreationDTO newCustomerData);

    CustomerDTO updateCustomer(Long customerId, CustomerUpdateDTO customerData);

    CustomerDTO getCustomerById(Long customerId);

    CustomerDTO getCustomerByDocument(Long documentTypeId, String document);

    List<CustomerTypeDTO> getCustomerTypes();

    List<DocumentTypeDTO> getDocumentTypes();

    ByteArrayInputStream loadCSV();

    List<CustomerRowDTO> saveCSV(MultipartFile file) throws IOException;
}
