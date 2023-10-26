package com.segurosbolivar.customerservice.repository;

import com.segurosbolivar.customerservice.dto.CustomerRowDTO;
import com.segurosbolivar.customerservice.model.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT CUSTOMER_ID, CUSTOMER_TYPE_ID, NAME, LAST_NAME, BIRTHDATE, DOCUMENT_TYPE_ID, " +
            "DOCUMENT, ADDRESS, AREA_ID, EMAIL, PHONE_NUMBER, CREATED_AT " +
            "FROM CUSTOMER WHERE DOCUMENT_TYPE_ID = :documentTypeId AND DOCUMENT = :document")
    Customer getCustomerByDocument(Long documentTypeId, String document);

    @Query("SELECT c.CUSTOMER_TYPE_ID, c.NAME, c.LAST_NAME, c.BIRTHDATE, c.DOCUMENT_TYPE_ID, " +
            "c.DOCUMENT, c.ADDRESS, area.CODE, c.EMAIL, c.PHONE_NUMBER FROM CUSTOMER c " +
            "JOIN GEOGRAPHIC_AREA area ON area.AREA_ID = c.AREA_ID " +
            "ORDER BY c.CUSTOMER_ID ASC")
    List<CustomerRowDTO> findAllCustomersToFile();
}
