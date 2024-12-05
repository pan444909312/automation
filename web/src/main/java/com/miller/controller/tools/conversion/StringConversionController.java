package com.miller.controller.tools.conversion;

import com.miller.controller.tools.ResultVO;
import com.miller.controller.tools.product.service.StringConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/string/conversion")
public class StringConversionController {

    @Autowired
    private StringConversionService stringConversionService;

    @PostMapping("/toPublishingFormat")
    public ResultVO<String> toPublishingFormat(@RequestBody StringConversionDto body){
        final String resStr = stringConversionService.toPublishingFormat(body);
        return ResultVO.success(resStr);
    }


}
