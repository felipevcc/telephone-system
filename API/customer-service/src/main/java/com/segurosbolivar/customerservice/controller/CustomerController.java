package com.segurosbolivar.customerservice.controller;

import com.segurosbolivar.customerservice.dto.*;
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

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerCreationDTO newCustomerData) {
        CustomerDTO newCustomer = customerService.createCustomer(newCustomerData);
        if (newCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(newCustomer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerUpdateDTO customerData
    ) {
        CustomerDTO updateCustomer = customerService.updateCustomer(customerId, customerData);
        if (updateCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updateCustomer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        CustomerDTO foundCustomer = customerService.getCustomerById(customerId);
        if (foundCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundCustomer);
    }

    @GetMapping("/{documentType}/{document}")
    public ResponseEntity<CustomerDTO> getCustomerByDocument(
            @PathVariable Long documentType,
            @PathVariable String document
    ) {
        CustomerDTO foundCustomer = customerService.getCustomerByDocument(documentType, document);
        if (foundCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundCustomer);
    }

    @GetMapping("/customerTypes")
    public ResponseEntity<List<CustomerTypeDTO>> getAllCustomerTypes() {
        List<CustomerTypeDTO> customerTypes = customerService.getCustomerTypes();
        if (customerTypes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerTypes);
    }

    @GetMapping("/documentTypes")
    public ResponseEntity<List<DocumentTypeDTO>> getAllDocumentTypes() {
        List<DocumentTypeDTO> documentTypes = customerService.getDocumentTypes();
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
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
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
