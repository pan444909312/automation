package com.miller.controller.tools.excel;

import com.miller.service.tools.excel.ExcelImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/excel")
public class ExcelImportController {

    @Autowired
    private ExcelImportService excelImportService;

    @PostMapping("/import")
    public ResponseEntity<String> importExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("sheetName") String sheetName
           ) throws IOException {
        excelImportService.importAndUpdate(file, sheetName);
        return ResponseEntity.ok("导入并更新成功");
    }
} 