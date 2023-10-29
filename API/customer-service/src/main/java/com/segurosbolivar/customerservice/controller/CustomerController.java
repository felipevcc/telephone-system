package com.segurosbolivar.customerservice.controller;

import com.segurosbolivar.customerservice.dto.CustomerCreationDTO;
import com.segurosbolivar.customerservice.dto.CustomerRowDTO;
import com.segurosbolivar.customerservice.dto.CustomerUpdateDTO;
import com.segurosbolivar.customerservice.model.Customer;
import com.segurosbolivar.customerservice.model.CustomerType;
import com.segurosbolivar.customerservice.model.DocumentType;
import com.segurosbolivar.customerservice.service.CustomerService;
import com.segurosbolivar.customerservice.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerCreationDTO newCustomerData) {
        Customer newCustomer = customerService.createCustomer(newCustomerData);
        if (newCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(newCustomer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerUpdateDTO customerData
    ) {
        Customer updateCustomer = customerService.updateCustomer(customerId, customerData);
        if (updateCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updateCustomer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        Customer foundCustomer = customerService.getCustomerById(customerId);
        if (foundCustomer == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundCustomer);
    }

    @GetMapping("/{documentType}/{document}")
    public ResponseEntity<Customer> getCustomerByDocument(
            @PathVariable Long documentType,
            @PathVariable String document
    ) {
        Customer foundCustomer = customerService.getCustomerByDocument(documentType, document);
        if (foundCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundCustomer);
    }

    @GetMapping("/customerTypes")
    public ResponseEntity<List<CustomerType>> getAllCustomerTypes() {
        List<CustomerType> customerTypes = customerService.getCustomerTypes();
        if (customerTypes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerTypes);
    }

    @GetMapping("/documentTypes")
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {
        List<DocumentType> documentTypes = customerService.getDocumentTypes();
        if (documentTypes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(documentTypes);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getCSVFile() {
        String filename = "customers.csv";
        InputStreamResource file = new InputStreamResource(customerService.loadCSV());
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @PostMapping("/upload")
    public ResponseEntity<Resource> uploadFile(@RequestParam("file") MultipartFile file) {
        if (!CSVHelper.isCSV(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            List<CustomerRowDTO> invalidCustomers = customerService.saveCSV(file);
            if (invalidCustomers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            String filename = "invalid_customers.csv";
            InputStreamResource outFile = new InputStreamResource(CSVHelper.customersToCSV(invalidCustomers));
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/csv"))
                    .body(outFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}
