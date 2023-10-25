package com.segurosbolivar.customerservice.service;

import com.segurosbolivar.customerservice.dto.CustomerCreationDTO;
import com.segurosbolivar.customerservice.dto.CustomerUpdateDTO;
import com.segurosbolivar.customerservice.model.Customer;
import com.segurosbolivar.customerservice.model.CustomerType;
import com.segurosbolivar.customerservice.model.DocumentType;
import com.segurosbolivar.customerservice.repository.CustomerProcedureRepository;
import com.segurosbolivar.customerservice.repository.CustomerRepository;
import com.segurosbolivar.customerservice.repository.CustomerTypeRepository;
import com.segurosbolivar.customerservice.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerProcedureRepository customerProcRepository;

    @Autowired
    CustomerTypeRepository customerTypeRepository;

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Override
    public Customer createCustomer(CustomerCreationDTO newCustomerData) {
        Long newCustomerId = null;
        try {
            newCustomerId = customerProcRepository.createCustomer(newCustomerData);
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
            customerProcRepository.updateCustomer(customerId, customerData);
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
}
