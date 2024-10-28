package com.miller.controller;

import com.alibaba.fastjson.JSONObject;
import com.miller.entity.dto.StringConversionDto;
import com.miller.entity.vo.ResultVo;
import com.miller.service.StringConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/string/conversion")
public class StringConversionController {

    @Autowired
    private StringConversionService stringConversionService;

    @PostMapping("/toPublishingFormat")
    public ResultVo<String> toPublishingFormat(@RequestBody StringConversionDto body){
        final String resStr = stringConversionService.toPublishingFormat(body);
        return ResultVo.success(resStr);
    }


}
