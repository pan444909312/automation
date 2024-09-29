package com.miller.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mchange.v2.beans.BeansUtils;
import com.miller.common.util.Response;
import com.miller.common.util.Result;
import com.miller.entity.AutoCaseRoi;
import com.miller.entity.dto.AddAutoCaseRoiDTO;
import com.miller.entity.dto.PageAutoCaseRoiDto;
import com.miller.entity.vo.AutoCaseRoiVo;
import com.miller.service.AutoCaseRoiService;
import com.miller.util.TimestampUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 自动化用例ROI表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@RestController
@RequestMapping("/autoCaseRoi")
@Api(description = "自动化模块")
public class AutoCaseRoiController {

    @Autowired
    AutoCaseRoiService autoCaseRoiService;
    @Operation(description = "分页查询自动化用例roi数据")
    @PostMapping("/listAutoCase")
    public Result listAutoCase(@RequestBody PageAutoCaseRoiDto pageAutoCaseRoiDto) {
        System.out.println(pageAutoCaseRoiDto);
        Page<AutoCaseRoi> autoCaseRoiVoPage = new Page<>(pageAutoCaseRoiDto.getPageNo(), pageAutoCaseRoiDto.getPageSize());
        QueryWrapper<AutoCaseRoi> queryWrapper = new QueryWrapper<>();

        Page<AutoCaseRoi> page = autoCaseRoiService.page(autoCaseRoiVoPage, queryWrapper);


        List<AutoCaseRoi> records = page.getRecords();
        long total = page.getTotal();

        ArrayList<AutoCaseRoiVo> autoCaseRoiVoList = new ArrayList<>();
        AutoCaseRoiVo autoCaseRoiVo;
        for (AutoCaseRoi record : records) {
            autoCaseRoiVo = new AutoCaseRoiVo();
            BeanUtils.copyProperties(record,autoCaseRoiVo);
            autoCaseRoiVo.setCreateTime(TimestampUtils.timestampToDate(record.getCreateTime()));
            autoCaseRoiVo.setUpdateTime(TimestampUtils.timestampToDate(record.getUpdateTime()));

            autoCaseRoiVoList.add(autoCaseRoiVo);
        }

        System.out.println(total);
        System.out.println(records);


        return Result.success().data("total",total).data("list",autoCaseRoiVoList);
    }

    @ApiOperation(value = "添加roi")
    @PostMapping("/addAutoCaseRoi")
    public Response<List<AutoCaseRoi>> addAutoCaseRoi(@RequestBody AddAutoCaseRoiDTO addAutoCaseRoiDTO) {
        AutoCaseRoi autoCaseRoi = new AutoCaseRoi();
        BeanUtils.copyProperties(addAutoCaseRoiDTO,autoCaseRoi);
        autoCaseRoiService.save(autoCaseRoi);

        return Response.success(null);
    }

}
