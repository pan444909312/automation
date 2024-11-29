package com.miller.controller.tools.product.service;


import com.miller.controller.tools.conversion.StringConversionDto;

import java.util.List;
import java.util.Map;

public interface StringConversionService {

    String toPublishingFormat(StringConversionDto stringConversionDto);
    Map<String, List<String>> toServerGroup(String sourceValue);

}
