package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.model.MinimumTimeSetting;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSettingRepository extends CrudRepository<MinimumTimeSetting, Long> {

    @Query("SELECT TIME_ID, TIME_VALUE, CREATED_AT " +
            "FROM (SELECT TIME_ID, TIME_VALUE, CREATED_AT, ROW_NUMBER() OVER (ORDER BY CREATED_AT DESC) AS rn FROM MINIMUM_TIME_SETTING) " +
            "WHERE rn = 1")
    MinimumTimeSetting getLatestTimeSetting();

    @Modifying
    @Query("INSERT INTO MINIMUM_TIME_SETTING (TIME_ID, TIME_VALUE) VALUES (SEQ_MINIMUM_TIME_SETTING.NEXTVAL, :timeValue)")
    void createTimeValue(Integer timeValue);
}
