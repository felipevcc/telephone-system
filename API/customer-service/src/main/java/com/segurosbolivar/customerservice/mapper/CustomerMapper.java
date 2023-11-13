package com.segurosbolivar.customerservice.mapper;

import com.segurosbolivar.customerservice.dto.CustomerDTO;
import com.segurosbolivar.customerservice.dto.CustomerRowDTO;
import com.segurosbolivar.customerservice.dto.CustomerTypeDTO;
import com.segurosbolivar.customerservice.dto.DocumentTypeDTO;
import com.segurosbolivar.customerservice.model.Customer;
import com.segurosbolivar.customerservice.model.CustomerType;
import com.segurosbolivar.customerservice.model.DocumentType;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerMapper {

    @Autowired
    ModelMapper modelMapper;

    public CustomerDTO customerToDTO(Customer customer) {
        CustomerDTO mappedCustomer = modelMapper.map(customer, CustomerDTO.class);
        mappedCustomer.setBirthdate(mappedCustomer.getBirthdate().split("T")[0]);
        return mappedCustomer;
    }

    public CustomerTypeDTO customerTypeToDTO(CustomerType customerType) {
        return modelMapper.map(customerType, CustomerTypeDTO.class);
    }

    public DocumentTypeDTO documentTypeToDTO(DocumentType documentType) {
        return modelMapper.map(documentType, DocumentTypeDTO.class);
    }

    public CustomerRowDTO csvRecordToDTO(CSVRecord csvRecord) {
        CustomerRowDTO customerRow = new CustomerRowDTO();
        customerRow.setCustomerTypeId(Long.parseLong(csvRecord.get("CUSTOMER_TYPE_ID")));
        customerRow.setName(csvRecord.get("NAME"));
        customerRow.setLastName(csvRecord.get("LAST_NAME"));
        customerRow.setBirthdate(csvRecord.get("BIRTHDATE"));
        customerRow.setDocumentTypeId(Long.parseLong(csvRecord.get("DOCUMENT_TYPE_ID")));
        customerRow.setDocument(csvRecord.get("DOCUMENT"));
        customerRow.setAddress(csvRecord.get("ADDRESS"));
        customerRow.setAreaCode(Long.parseLong(csvRecord.get("AREA_CODE")));
        customerRow.setEmail(csvRecord.get("EMAIL"));
        customerRow.setPhoneNumber(csvRecord.get("PHONE_NUMBER"));
        return customerRow;
    }
}
