package com.segurosbolivar.customerservice.service;

import com.segurosbolivar.customerservice.dto.*;
import com.segurosbolivar.customerservice.mapper.CustomerMapper;
import com.segurosbolivar.customerservice.model.Customer;
import com.segurosbolivar.customerservice.model.CustomerType;
import com.segurosbolivar.customerservice.model.DocumentType;
import com.segurosbolivar.customerservice.repository.CustomerCallRepository;
import com.segurosbolivar.customerservice.repository.CustomerRepository;
import com.segurosbolivar.customerservice.repository.CustomerTypeRepository;
import com.segurosbolivar.customerservice.repository.DocumentTypeRepository;
import com.segurosbolivar.customerservice.util.CSVHelper;
import com.segurosbolivar.customerservice.util.DateFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public CustomerDTO createCustomer(CustomerCreationDTO newCustomerData) {
        try {
            // Birthdate validation
            String birthdate = DateFormat.dateStringFormat(newCustomerData.getBirthdate());
            if (birthdate == null) {
                return null;
            }
            newCustomerData.setBirthdate(birthdate);

            Long newCustomerId = customerCallRepository.createCustomer(newCustomerData);
            if (newCustomerId == null) {
                return null;
            }
            return getCustomerById(newCustomerId);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerDTO updateCustomer(Long customerId, CustomerUpdateDTO customerData) {
        try {
            if (!customerRepository.existsById(customerId)) {
                return null;
            }
            customerCallRepository.updateCustomer(customerId, customerData);
            return getCustomerById(customerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        Customer foundCustomer = customerRepository.findById(customerId).orElse(null);
        if (foundCustomer == null) {
            return null;
        }
        return customerMapper.customerToDTO(foundCustomer);
    }

    @Override
    public CustomerDTO getCustomerByDocument(Long documentTypeId, String document) {
        Customer foundCustomer = customerRepository.getCustomerByDocument(documentTypeId, document);
        if (foundCustomer == null) {
            return null;
        }
        return customerMapper.customerToDTO(foundCustomer);
    }

    @Override
    public List<CustomerTypeDTO> getCustomerTypes() {
        List<CustomerType> customerTypes = customerTypeRepository.findAllCustomerTypes();
        return customerTypes.stream()
                .map(customerType -> customerMapper.customerTypeToDTO(customerType))
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentTypeDTO> getDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepository.findAllDocumentTypes();
        return documentTypes.stream()
                .map(documentType -> customerMapper.documentTypeToDTO(documentType))
                .collect(Collectors.toList());
    }

    @Override
    public ByteArrayInputStream loadCSV() {
        List<CustomerRowDTO> customers = customerCallRepository.findAllCustomersToFile();
        return CSVHelper.customersToCSV(customers);
    }

    @Override
    public List<CustomerRowDTO> saveCSV(MultipartFile file) {
        try (InputStream inputFile = file.getInputStream();
             BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputFile, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                     .builder().setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build())) {

            List<CustomerRowDTO> invalidCustomers = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                CustomerRowDTO customerRow = customerMapper.csvRecordToDTO(csvRecord);

                Long customerId = customerRepository.getCustomerIdByDocument(csvRecord.get("DOCUMENT"));
                String birthdate = DateFormat.dateStringFormat(csvRecord.get("BIRTHDATE"));
                Long areaCode = customerRepository.getAreaIdByAreaCode(Long.parseLong(csvRecord.get("AREA_CODE")));
                if (customerId != null || areaCode == null || birthdate == null) {
                    invalidCustomers.add(customerRow);
                    continue;
                }

                CustomerCreationDTO customerCreationData = new CustomerCreationDTO();
                customerCreationData.setCustomerTypeId(Long.parseLong(csvRecord.get("CUSTOMER_TYPE_ID")));
                customerCreationData.setName(csvRecord.get("NAME"));
                customerCreationData.setLastName(csvRecord.get("LAST_NAME"));
                customerCreationData.setBirthdate(birthdate);
                customerCreationData.setDocumentTypeId(Long.parseLong(csvRecord.get("DOCUMENT_TYPE_ID")));
                customerCreationData.setDocument(csvRecord.get("DOCUMENT"));
                customerCreationData.setAddress(csvRecord.get("ADDRESS"));
                customerCreationData.setAreaId(areaCode);
                customerCreationData.setEmail(csvRecord.get("EMAIL"));
                customerCreationData.setPhoneNumber(csvRecord.get("PHONE_NUMBER"));

                Long newCustomerId = customerCallRepository.createCustomer(customerCreationData);
                if (newCustomerId == null) {
                    invalidCustomers.add(customerRow);
                }
            }
            return invalidCustomers;

        } catch (IOException e) {
            throw new RuntimeException("Failed to process CSV file " + e.getMessage());
        }
    }
}
