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
            "FROM TELEPHONE_NUMBER_AUDIT " +
            "WHERE PHONE_NUMBER = :phoneNumber " +
            "AND RELEASE_DATE = (SELECT MAX(RELEASE_DATE) FROM TELEPHONE_NUMBER_AUDIT WHERE PHONE_NUMBER = :phoneNumber)")
    TelephoneNumber findLatestTelephoneNumberRelease(Integer phoneNumber);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE " +
            "FROM TELEPHONE_NUMBER WHERE CUSTOMER_ID = :customerId")
    TelephoneNumber findTelephoneNumberByCustomer(Long customerId);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE " +
            "FROM TELEPHONE_NUMBER_AUDIT WHERE CUSTOMER_ID = :customerId " +
            "ORDER BY ASSIGNMENT_DATE ASC")
    List<TelephoneNumber> findNumberHistoryByCustomer(Long customerId);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE " +
            "FROM TELEPHONE_NUMBER_AUDIT WHERE PHONE_NUMBER = :phoneNumber " +
            "ORDER BY ASSIGNMENT_DATE ASC")
    List<TelephoneNumber> findTelephoneNumberHistory(Integer phoneNumber);

    @Query("SELECT COUNT(*) FROM TELEPHONE_NUMBER WHERE CENTER_ID = :centerId")
    Integer countNumbersAssignedByCenter(Long centerId);
}
