package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumberAudit;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelephoneNumberAuditRepository extends CrudRepository<TelephoneNumberAudit, Long> {

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE, IS_ACTIVE " +
            "FROM TELEPHONE_NUMBER_AUDIT " +
            "WHERE PHONE_NUMBER = :phoneNumber " +
            "AND RELEASE_DATE = (SELECT MAX(RELEASE_DATE) FROM TELEPHONE_NUMBER_AUDIT WHERE PHONE_NUMBER = :phoneNumber)")
    TelephoneNumberAudit findLatestTelephoneNumberRelease(Integer phoneNumber);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE, IS_ACTIVE " +
            "FROM TELEPHONE_NUMBER_AUDIT " +
            "WHERE PHONE_NUMBER = :phoneNumber " +
            "AND IS_ACTIVE = 1")
    TelephoneNumberAudit findReleasedTelephoneNumber(Integer phoneNumber);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE, IS_ACTIVE " +
            "FROM TELEPHONE_NUMBER_AUDIT WHERE CUSTOMER_ID = :customerId " +
            "ORDER BY ASSIGNMENT_DATE ASC")
    List<TelephoneNumberAudit> findNumberHistoryByCustomer(Long customerId);

    @Query("SELECT NUMBER_RECORD_ID, CENTER_ID, CUSTOMER_ID, PHONE_NUMBER, ASSIGNMENT_DATE, RELEASE_DATE, IS_ACTIVE " +
            "FROM TELEPHONE_NUMBER_AUDIT WHERE PHONE_NUMBER = :phoneNumber " +
            "ORDER BY ASSIGNMENT_DATE ASC")
    List<TelephoneNumberAudit> findTelephoneNumberHistory(Integer phoneNumber);
}
