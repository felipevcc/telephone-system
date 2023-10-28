package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelephoneNumberRepository extends CrudRepository<TelephoneNumber, Long> {
    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE " +
            "FROM TELEPHONE_NUMBER WHERE PHONE_NUMBER = :phoneNumber")
    TelephoneNumber findTelephoneNumber(Integer phoneNumber);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE " +
            "FROM TELEPHONE_NUMBER WHERE CUSTOMER_ID = :customerId")
    TelephoneNumber findTelephoneNumberByCustomer(Long customerId);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE " +
            "FROM TELEPHONE_NUMBER_AUDIT WHERE CUSTOMER_ID = :customerId")
    List<TelephoneNumber> findNumberHistoryByCustomer(Long customerId);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE " +
            "FROM TELEPHONE_NUMBER_AUDIT WHERE PHONE_NUMBER = :phoneNumber")
    List<TelephoneNumber> findTelephoneNumberHistory(Integer phoneNumber);
}
