package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Query("SELECT COUNT(*) FROM TELEPHONE_NUMBER " +
            "WHERE PHONE_NUMBER >= :initialRangeNumber AND PHONE_NUMBER <= :finalRangeNumber")
    Integer countNumbersAssignedByRange(Integer initialRangeNumber, Integer finalRangeNumber);

    @Query("WITH TEL_RANGE AS (SELECT LEVEL + :initialRangeNumber - 1 AS tel_number FROM DUAL CONNECT BY LEVEL <= :finalRangeNumber) " +
            "SELECT tel_number FROM TEL_RANGE " +
            "WHERE tel_number NOT IN (SELECT PHONE_NUMBER FROM TELEPHONE_NUMBER) " +
            "AND tel_number NOT IN (SELECT PHONE_NUMBER FROM TELEPHONE_NUMBER_AUDIT WHERE IS_ACTIVE = 1) " +
            "ORDER BY tel_number ASC")
    List<Integer> findAvailableNumbersByRange(Integer initialRangeNumber, Integer finalRangeNumber);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE " +
            "FROM TELEPHONE_NUMBER WHERE NUMBER_RECORD_ID = :numberRecordId")
    TelephoneNumber findTelephoneNumberById(Long numberRecordId);
}
