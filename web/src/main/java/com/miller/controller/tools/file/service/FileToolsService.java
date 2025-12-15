package com.miller.controller.tools.file.service;

public interface FileToolsService {

    String generateAndUpload(long size, String unit, boolean fillRandom,String suffix);
}
