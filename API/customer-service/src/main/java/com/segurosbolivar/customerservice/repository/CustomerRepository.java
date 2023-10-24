package com.segurosbolivar.customerservice.repository;

import com.segurosbolivar.customerservice.model.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT c FROM CUSTOMER c WHERE DOCUMENT_TYPE_ID = :documentTypeId AND DOCUMENT = :document")
    Customer getCustomerByDocument(Long documentTypeId, String document);

    @Query("SELECT PHONE_NUMBER FROM TELEPHONE_NUMBER WHERE CUSTOMER_ID = :customerId")
    String getAssignedTelephoneNumber(Long customerId);
}
