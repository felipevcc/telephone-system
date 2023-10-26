package com.segurosbolivar.customerservice.service;

import com.segurosbolivar.customerservice.dto.CustomerCreationDTO;
import com.segurosbolivar.customerservice.dto.CustomerRowDTO;
import com.segurosbolivar.customerservice.dto.CustomerUpdateDTO;
import com.segurosbolivar.customerservice.model.Customer;
import com.segurosbolivar.customerservice.model.CustomerType;
import com.segurosbolivar.customerservice.model.DocumentType;
import com.segurosbolivar.customerservice.repository.CustomerCallRepository;
import com.segurosbolivar.customerservice.repository.CustomerRepository;
import com.segurosbolivar.customerservice.repository.CustomerTypeRepository;
import com.segurosbolivar.customerservice.repository.DocumentTypeRepository;
import com.segurosbolivar.customerservice.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerCallRepository customerCallRepository;

    @Autowired
    CustomerTypeRepository customerTypeRepository;

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Override
    public Customer createCustomer(CustomerCreationDTO newCustomerData) {
        try {
            Long newCustomerId = customerCallRepository.createCustomer(newCustomerData);
            if (newCustomerId == null) {
                return null;
            }
            return customerRepository.findById(newCustomerId).orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Customer updateCustomer(Long customerId, CustomerUpdateDTO customerData) {
        try {
            if (!customerRepository.existsById(customerId)) {
                return null;
            }
            customerCallRepository.updateCustomer(customerId, customerData);
            return customerRepository.findById(customerId).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Customer getCustomerByDocument(Long documentTypeId, String document) {
        return customerRepository.getCustomerByDocument(documentTypeId, document);
    }

    @Override
    public List<CustomerType> getCustomerTypes() {
        return customerTypeRepository.findAllCustomerTypes();
    }

    @Override
    public List<DocumentType> getDocumentTypes() {
        return documentTypeRepository.findAllDocumentTypes();
    }

    @Override
    public ByteArrayInputStream loadCSV() {
        List<CustomerRowDTO> customers = customerCallRepository.findAllCustomersToFile();
        return CSVHelper.customersToCSV(customers);
    }
}
