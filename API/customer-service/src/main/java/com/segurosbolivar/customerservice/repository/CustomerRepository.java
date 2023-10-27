package com.segurosbolivar.customerservice.repository;

import com.segurosbolivar.customerservice.model.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT CUSTOMER_ID, CUSTOMER_TYPE_ID, NAME, LAST_NAME, BIRTHDATE, DOCUMENT_TYPE_ID, " +
            "DOCUMENT, ADDRESS, AREA_ID, EMAIL, PHONE_NUMBER, CREATED_AT " +
            "FROM CUSTOMER WHERE DOCUMENT_TYPE_ID = :documentTypeId AND DOCUMENT = :document")
    Customer getCustomerByDocument(Long documentTypeId, String document);

    @Query("SELECT CUSTOMER_ID FROM CUSTOMER WHERE DOCUMENT = :document")
    Long getCustomerIdByDocument(String document);

    @Query("SELECT AREA_ID FROM GEOGRAPHIC_AREA WHERE CODE = :areaCode")
    Long getAreaIdByAreaCode(Long areaCode);
}
