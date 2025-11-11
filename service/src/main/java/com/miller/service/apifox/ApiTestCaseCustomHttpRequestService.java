package com.miller.service.apifox;

import com.miller.entity.apifox.ApiTestCaseCustomHttpRequestEntity;

import java.util.List;
import java.util.Set;

public interface ApiTestCaseCustomHttpRequestService  {

     ApiTestCaseCustomHttpRequestEntity queryById(String id);

     List<ApiTestCaseCustomHttpRequestEntity> queryByIdList(Set<String> ids);

}

