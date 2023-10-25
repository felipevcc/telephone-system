package com.segurosbolivar.customerservice.controller;

import com.segurosbolivar.customerservice.dto.CustomerCreationDTO;
import com.segurosbolivar.customerservice.dto.CustomerUpdateDTO;
import com.segurosbolivar.customerservice.model.Customer;
import com.segurosbolivar.customerservice.model.CustomerType;
import com.segurosbolivar.customerservice.model.DocumentType;
import com.segurosbolivar.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
