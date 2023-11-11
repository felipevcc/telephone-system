package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.model.MinimumTimeSetting;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSettingRepository extends CrudRepository<MinimumTimeSetting, Long> {

    @Query("SELECT TIME_ID, TIME_VALUE, CREATED_AT FROM MINIMUM_TIME_SETTING " +
            "WHERE TIME_ID = (SELECT MAX(TIME_ID) FROM MINIMUM_TIME_SETTING)")
    MinimumTimeSetting getLatestTimeSetting();

    @Modifying
    @Query("INSERT INTO MINIMUM_TIME_SETTING (TIME_ID, TIME_VALUE) VALUES (SEQ_MINIMUM_TIME_SETTING.NEXTVAL, :timeValue)")
    void createTimeValue(Integer timeValue);
}
