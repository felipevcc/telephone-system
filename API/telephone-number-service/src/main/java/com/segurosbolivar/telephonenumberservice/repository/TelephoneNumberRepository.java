package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelephoneNumberRepository extends CrudRepository<TelephoneNumber, Long> {
    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE " +
            "FROM TELEPHONE_NUMBER WHERE PHONE_NUMBER = :phoneNumber")
    TelephoneNumber findTelephoneNumber(Integer phoneNumber);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE " +
            "FROM TELEPHONE_NUMBER WHERE CUSTOMER_ID = :customerId")
    TelephoneNumber findTelephoneNumberByCustomer(Long customerId);

    @Query("SELECT COUNT(*) FROM TELEPHONE_NUMBER WHERE CENTER_ID = :centerId")
    Integer countNumbersAssignedByCenter(Long centerId);
}
