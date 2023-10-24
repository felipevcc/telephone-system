package com.segurosbolivar.customerservice.controller;

import com.segurosbolivar.customerservice.dto.CustomerDTO;
import com.segurosbolivar.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

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
}
