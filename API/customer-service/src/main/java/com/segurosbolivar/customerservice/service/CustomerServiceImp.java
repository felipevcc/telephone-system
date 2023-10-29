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
import java.util.Optional;

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
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
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

    @Override
    public List<CustomerRowDTO> saveCSV(MultipartFile file) {
        try (InputStream inputFile = file.getInputStream();
             BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputFile, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                     .builder().setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build())) {

            List<CustomerRowDTO> invalidCustomers = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
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

                Long customerId = customerRepository.getCustomerIdByDocument(csvRecord.get("DOCUMENT"));
                String birthdate = DateFormat.dateStringFormat(csvRecord.get("BIRTHDATE"));
                Long areaCode = customerRepository.getAreaIdByAreaCode(Long.parseLong(csvRecord.get("AREA_CODE")));
                if (customerId != null ||areaCode == null || birthdate == null) {
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
