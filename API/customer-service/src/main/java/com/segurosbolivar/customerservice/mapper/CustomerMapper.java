package com.segurosbolivar.customerservice.mapper;

import com.segurosbolivar.customerservice.dto.CustomerDTO;
import com.segurosbolivar.customerservice.dto.CustomerTypeDTO;
import com.segurosbolivar.customerservice.dto.DocumentTypeDTO;
import com.segurosbolivar.customerservice.model.Customer;
import com.segurosbolivar.customerservice.model.CustomerType;
import com.segurosbolivar.customerservice.model.DocumentType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerMapper {

    @Autowired
    ModelMapper modelMapper;

    public CustomerDTO customerToDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerTypeDTO customerTypeToDTO(CustomerType customerType) {
        return modelMapper.map(customerType, CustomerTypeDTO.class);
    }

    public DocumentTypeDTO documentTypeToDTO(DocumentType documentType) {
        return modelMapper.map(documentType, DocumentTypeDTO.class);
    }
}
