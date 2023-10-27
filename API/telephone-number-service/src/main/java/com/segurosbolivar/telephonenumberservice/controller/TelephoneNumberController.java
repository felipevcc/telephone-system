package com.segurosbolivar.telephonenumberservice.controller;

import com.segurosbolivar.telephonenumberservice.service.TelephoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("telephoneNumber")
@CrossOrigin
public class TelephoneNumberController {

    @Autowired
    TelephoneNumberService telephoneNumberService;
}
