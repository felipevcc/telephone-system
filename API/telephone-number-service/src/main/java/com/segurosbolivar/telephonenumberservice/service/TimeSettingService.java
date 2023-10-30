package com.segurosbolivar.telephonenumberservice.service;

import com.segurosbolivar.telephonenumberservice.model.MinimumTimeSetting;

public interface TimeSettingService {

    MinimumTimeSetting getTimeSetting();

    MinimumTimeSetting createTimeSetting(Integer timeValue);
}
