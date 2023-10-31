package com.segurosbolivar.telephonenumberservice.mapper;

import com.segurosbolivar.telephonenumberservice.dto.TelephoneNumberDTO;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import com.segurosbolivar.telephonenumberservice.model.TelephoneNumberAudit;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelephoneNumberMapper {

    @Autowired
    ModelMapper modelMapper;

    public TelephoneNumberDTO telNumberToDTO(TelephoneNumber telephoneNumber) {
        modelMapper.addMappings(new PropertyMap<TelephoneNumber, TelephoneNumberDTO>() {
            @Override
            protected void configure() {
                skip(destination.getReleaseDate());
            }
        });
        return modelMapper.map(telephoneNumber, TelephoneNumberDTO.class);
    }

    /*public TelephoneNumberDTO telNumberToDTO(TelephoneNumber telephoneNumber) {
        TelephoneNumberDTO mapTelNumber = new TelephoneNumberDTO();
        mapTelNumber.setNumberRecordId(telephoneNumber.getNumberRecordId());
        mapTelNumber.setCenterId(telephoneNumber.getCenterId());
        mapTelNumber.setCustomerId(telephoneNumber.getCustomerId());
        mapTelNumber.setPhoneNumber(telephoneNumber.getPhoneNumber());
        mapTelNumber.setAssignmentDate(telephoneNumber.getAssignmentDate());
        mapTelNumber.setReleaseDate(null);
        return mapTelNumber;
    }*/

    public TelephoneNumberDTO telNumberAuditToDTO(TelephoneNumberAudit telephoneNumberAudit) {
        return modelMapper.map(telephoneNumberAudit, TelephoneNumberDTO.class);
    }

    /*public TelephoneNumberDTO telNumberAuditToDTO(TelephoneNumberAudit telephoneNumber) {
        TelephoneNumberDTO mapTelNumber = new TelephoneNumberDTO();
        mapTelNumber.setNumberRecordId(telephoneNumber.getNumberRecordId());
        mapTelNumber.setCenterId(telephoneNumber.getCenterId());
        mapTelNumber.setCustomerId(telephoneNumber.getCustomerId());
        mapTelNumber.setPhoneNumber(telephoneNumber.getPhoneNumber());
        mapTelNumber.setAssignmentDate(telephoneNumber.getAssignmentDate());
        mapTelNumber.setReleaseDate(null);
        return mapTelNumber;
    }*/
}
