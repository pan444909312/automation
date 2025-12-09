package com.miller.controller.tools.file;

import com.miller.controller.tools.ResultVO;
import com.miller.controller.tools.file.service.FileToolsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/tools/file")
public class FileToolsController {

    @Resource
    private FileToolsService fileToolsService;

    @PostMapping("/generate/upload")
    public ResultVO generateAndUpload(@RequestParam long size, String unit, String suffix){
        String url = fileToolsService.generateAndUpload(size, unit, false,suffix);
        return ResultVO.success(url);
    }

}
