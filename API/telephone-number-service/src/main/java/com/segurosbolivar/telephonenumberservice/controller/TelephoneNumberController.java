package com.segurosbolivar.telephonenumberservice.controller;

import com.segurosbolivar.telephonenumberservice.model.MinimumTimeSetting;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import com.segurosbolivar.telephonenumberservice.service.TelephoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("telephoneNumber")
@CrossOrigin
public class TelephoneNumberController {

    @Autowired
    TelephoneNumberService telephoneNumberService;

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<TelephoneNumber> getTelephoneNumber(@PathVariable Integer phoneNumber) {
        TelephoneNumber telephoneNumber = telephoneNumberService.getTelephoneNumber(phoneNumber);
        // Check if the telephone number has never been assigned
        if (telephoneNumber == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(telephoneNumber);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<TelephoneNumber> getTelephoneNumberByCustomer(@PathVariable Long customerId) {
        TelephoneNumber telephoneNumber = telephoneNumberService.getTelephoneNumberByCustomer(customerId);
        // Check if the customer does not have a number
        if (telephoneNumber == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(telephoneNumber);
    }

    @PostMapping("/numberTracking")
    public ResponseEntity<String> numberTrackingProcess() {
        telephoneNumberService.runNumberTrackingProcess();
        return ResponseEntity.status(HttpStatus.OK).body("Executed process");
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<TelephoneNumber> assignTelephoneNumber(@PathVariable Long customerId) {
        TelephoneNumber assignedTelephoneNumber = telephoneNumberService.assignTelephoneNumber(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(assignedTelephoneNumber);
    }

    @PutMapping("/{phoneNumber}")
    public ResponseEntity<TelephoneNumber> releaseTelephoneNumber(@PathVariable Integer phoneNumber) {
        try {
            TelephoneNumber releasedTelephoneNumber = telephoneNumberService.releaseTelephoneNumber(phoneNumber);
            // Check if the telephone number had already been released
            if (releasedTelephoneNumber == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(releasedTelephoneNumber);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/downloadCustomerHistory/{customerId}")
    public ResponseEntity<Resource> getCustomerHistoryCSV(@PathVariable Long customerId) {
        String filename = "customer_history.csv";
        InputStreamResource file = new InputStreamResource(telephoneNumberService.loadCustomerHistoryCSV(customerId));
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping("/downloadNumberHistory/{phoneNumber}")
    public ResponseEntity<Resource> getNumberHistoryCSV(@PathVariable Integer phoneNumber) {
        String filename = "number_history.csv";
        InputStreamResource file = new InputStreamResource(telephoneNumberService.loadNumberHistoryCSV(phoneNumber));
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping("/timeSetting")
    public ResponseEntity<MinimumTimeSetting> getTimeSetting() {
        return ResponseEntity.status(HttpStatus.OK).body(telephoneNumberService.getTimeSetting());
    }

    @PostMapping("/timeSetting/{timeValue}")
    public ResponseEntity<MinimumTimeSetting> createTimeSetting(@PathVariable Integer timeValue) {
        return ResponseEntity.status(HttpStatus.OK).body(telephoneNumberService.createTimeSetting(timeValue));
    }
}
