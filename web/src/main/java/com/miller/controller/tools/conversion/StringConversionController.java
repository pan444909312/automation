package com.miller.controller.tools.conversion;

import com.miller.controller.tools.ResultVO;
import com.miller.controller.tools.dao.ToolsBaseReqDao;
import com.miller.controller.tools.product.service.StringConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/string/conversion")
public class StringConversionController {

    @Autowired
    private StringConversionService stringConversionService;

    @PostMapping("/toPublishingFormat")
    public ResultVO toPublishingFormat(@RequestBody ToolsBaseReqDao<StringConversionDto> body) {
        StringConversionDto conversionDto = body.getBody();
        if (ObjectUtils.isEmpty(conversionDto)) {
            return ResultVO.failed("值不能为空");
        }

        final String resStr = stringConversionService.toPublishingFormat(conversionDto);
        return ResultVO.success(resStr);
    }


}
