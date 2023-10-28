package com.segurosbolivar.telephonenumberservice.controller;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import com.segurosbolivar.telephonenumberservice.service.TelephoneNumberService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("telephoneNumber")
@CrossOrigin
public class TelephoneNumberController {

    @Autowired
    TelephoneNumberService telephoneNumberService;

    @PostMapping("/numberTracking")
    public ResponseEntity<Object> numberTrackingProcess() {
        telephoneNumberService.runNumberTrackingProcess();
        return ResponseEntity.status(HttpStatus.OK).body("Executed process");
    }

    @PutMapping("/{phoneNumber}")
    public ResponseEntity<TelephoneNumber> releaseTelephoneNumber(@PathParam("phoneNumber") Integer phoneNumber) {
        try {
            TelephoneNumber releasedTelephoneNumber = telephoneNumberService.releaseTelephoneNumber(phoneNumber);
            // Check if the phone number does not exist or had already been released
            if (releasedTelephoneNumber == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(releasedTelephoneNumber);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<TelephoneNumber> getTelephoneNumberByCustomer(@PathParam("customerId") Long customerId) {
        TelephoneNumber telephoneNumber = telephoneNumberService.getTelephoneNumberByCustomer(customerId);
        // Check if the customer does not have a number
        if (telephoneNumber == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(telephoneNumber);
    }
}
