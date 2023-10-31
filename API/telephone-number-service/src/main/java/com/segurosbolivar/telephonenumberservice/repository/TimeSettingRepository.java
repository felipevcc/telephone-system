package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.model.MinimumTimeSetting;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSettingRepository extends CrudRepository<MinimumTimeSetting, Long> {
    @Query("SELECT TIME_ID, TIME_VALUE, CREATED_AT FROM MINIMUM_TIME_SETTING " +
            "WHERE CREATED_AT = (SELECT MAX(CREATED_AT) FROM MINIMUM_TIME_SETTING)")
    MinimumTimeSetting getLatestTimeSetting();

    @Query("INSERT INTO MINIMUM_TIME_SETTING (TIME_VALUE) VALUES (:timeValue)")
    void createTimeValue(Integer timeValue);
}