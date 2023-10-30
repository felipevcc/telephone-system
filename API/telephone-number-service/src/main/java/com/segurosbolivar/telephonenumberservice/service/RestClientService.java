package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.dto.CenterDTO;
import com.segurosbolivar.telephonenumberservice.dto.CustomerDTO;
import com.segurosbolivar.telephonenumberservice.dto.DocumentTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestClientService {

    @Autowired
    RestTemplate restTemplate;

    public CustomerDTO getCustomerById(Long customerId) {
        return restTemplate.getForObject(
                "http://CUSTOMER-SERVICE/api/v1/customer/{customerId}",
                CustomerDTO.class,
                customerId
        );
    }

    public List<CenterDTO> getAllCentersByArea(Long areaId) {
        ResponseEntity<List<CenterDTO>> responseEntity = restTemplate.exchange(
                "http://CENTER-SERVICE/api/v1/center/area/{areaId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CenterDTO>>() {},
                areaId
        );
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    public String getDocumentTypeCode(Long documentTypeId) {
        // Get document type abbreviation from client
        List<DocumentTypeDTO> documentTypes = restTemplate.exchange(
                "http://CUSTOMER-SERVICE/api/v1/customer/documentTypes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DocumentTypeDTO>>() {}
        ).getBody();
        if (documentTypes != null) {
            return documentTypes.stream()
                    .filter(dt -> dt.getDocumentTypeId().equals(documentTypeId))
                    .map(DocumentTypeDTO::getTypeCode)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
