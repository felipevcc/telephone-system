package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.model.MinimumTimeSetting;
import com.segurosbolivar.telephonenumberservice.repository.TimeSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TimeSettingServiceImp implements TimeSettingService {

    @Autowired
    TimeSettingRepository timeSettingRepository;

    @Override
    public MinimumTimeSetting getTimeSetting() {
        return timeSettingRepository.getLatestTimeSetting();
    }

    @Override
    public MinimumTimeSetting createTimeSetting(Integer timeValue) {
        timeSettingRepository.createTimeValue(timeValue);
        return timeSettingRepository.getLatestTimeSetting();
    }
}
