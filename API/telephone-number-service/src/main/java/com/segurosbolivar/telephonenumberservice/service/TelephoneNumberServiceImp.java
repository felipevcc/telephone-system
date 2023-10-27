package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.repository.TelephoneNumberCallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelephoneNumberServiceImp implements TelephoneNumberService {

    @Autowired
    TelephoneNumberCallRepository telephoneNumberRepository;
}
