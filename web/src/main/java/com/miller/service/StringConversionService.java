package com.miller.service;

import com.miller.entity.dto.StringConversionDto;

import java.util.List;
import java.util.Map;

public interface StringConversionService {

    String toPublishingFormat(StringConversionDto stringConversionDto);
    Map<String, List<String>> toServerGroup(String sourceValue);

}
