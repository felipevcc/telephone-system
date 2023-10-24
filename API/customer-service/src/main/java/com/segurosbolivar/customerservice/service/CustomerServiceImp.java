package com.segurosbolivar.customerservice.service;

import com.segurosbolivar.customerservice.dto.CustomerDTO;
import com.segurosbolivar.customerservice.model.Customer;
import com.segurosbolivar.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerDTO getCustomerByDocument(Long documentTypeId, String document){
        Customer foundCustomer = customerRepository.getCustomerByDocument(documentTypeId, document);
        if (foundCustomer == null) {
            return null;
        }
        String assignedTelephoneNumber = customerRepository.getAssignedTelephoneNumber(foundCustomer.getCustomerId());

        CustomerDTO customerResponse = new CustomerDTO();
        customerResponse.setCustomerId(foundCustomer.getCustomerId());
        customerResponse.setCustomerTypeId(foundCustomer.getCustomerTypeId());
        customerResponse.setName(foundCustomer.getName());
        customerResponse.setLastName(foundCustomer.getLastName());
        customerResponse.setBirthdate(foundCustomer.getBirthdate());
        customerResponse.setDocumentTypeId(foundCustomer.getDocumentTypeId());
        customerResponse.setDocument(foundCustomer.getDocument());
        customerResponse.setAddress(foundCustomer.getAddress());
        customerResponse.setAreaId(foundCustomer.getAreaId());
        customerResponse.setEmail(foundCustomer.getEmail());
        customerResponse.setPhoneNumber(foundCustomer.getPhoneNumber());
        customerResponse.setTelephoneNumber(assignedTelephoneNumber);

        return customerResponse;
    }

}
