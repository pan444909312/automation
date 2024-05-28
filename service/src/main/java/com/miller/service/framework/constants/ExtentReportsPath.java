package com.miller.service.framework.constants;

import java.io.File;

public class ExtentReportsPath {
    public  static final String REPORTS_LOCATION=System.getProperty("user.dir")+ File.separator+"service"+File.separator+"target"+File.separator
            +"ExtentReports"+ File.separator+"TestReport.html";
}
