package com.miller.bdm.app.privateShop.callback.flow;


import com.miller.bdm.app.privateShop.callback.request.SignCallBackRequestDTO;
import com.miller.bdm.app.privateShop.callback.response.SignCallBackResponseDTO;
import com.miller.bdm.app.privateShop.sign.response.SignResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.http.HttpUtils;
import com.panda.erp.server.api.constant.CrmEnum;
import com.panda.erp.server.common.dto.eversign.EsEventMetaRequest;
import com.panda.erp.server.common.dto.eversign.EsEventSignerRequest;

/**
 * 流程_bdm- 签约回调
 *
 * @author lipan
 * @version 1.0
 * @since 2024/1/2 11:10:46
 */


public class SignCallBackFlow {
    /**
     * bdm-签约回调
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/yida/eversign/event/accept";

    /**
     * bdm- 签约回调
     */
    public static SignCallBackResponseDTO CallBack(SignCallBackRequestDTO signCallBackRequestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(signCallBackRequestDTO), null, SignCallBackResponseDTO.class);
    }
    /**
     *签署人签署文档
     * @return
     */
    public static SignCallBackResponseDTO signCallBackSign(){
        SignCallBackRequestDTO signCallBackRequestDTO = callBackData();
        signCallBackRequestDTO.setEventType(CrmEnum.EverSignEventEnum.SIGNED.getEventType());

        signCallBackRequestDTO.getSigner().setId("1");
        signCallBackRequestDTO.getSigner().setOrder("1");
        return CallBack(signCallBackRequestDTO);
    }

    /**
     *发送文档给签署人
     * @return
     */
    public static SignCallBackResponseDTO signCallBackSend() {
        SignCallBackRequestDTO signCallBackRequestDTO = callBackData();
        signCallBackRequestDTO.setEventType(CrmEnum.EverSignEventEnum.SEND.getEventType());

        signCallBackRequestDTO.getSigner().setId("2");
        signCallBackRequestDTO.getSigner().setOrder("2");
        return CallBack(signCallBackRequestDTO);
    }

    /**
     *签署人查看文档
     * @return
     */
    public static SignCallBackResponseDTO SignCallBackViewed() {
        SignCallBackRequestDTO signCallBackRequestDTO = callBackData();
        signCallBackRequestDTO.setEventType(CrmEnum.EverSignEventEnum.VIEWED.getEventType());

        signCallBackRequestDTO.getSigner().setId("2");
        signCallBackRequestDTO.getSigner().setOrder("2");
        return  CallBack(signCallBackRequestDTO);
    }

    /**
     *文档签署完成
     * @return
     */
    public static SignCallBackResponseDTO SignCallBackCompleted() {
        SignCallBackRequestDTO signCallBackRequestDTO = callBackData();
        signCallBackRequestDTO.setEventType(CrmEnum.EverSignEventEnum.COMPLETED.getEventType());

        signCallBackRequestDTO.getSigner().setId("2");
        signCallBackRequestDTO.getSigner().setOrder("2");
        return  CallBack(signCallBackRequestDTO);
    }

    public static SignCallBackRequestDTO callBackData(){
        SignCallBackRequestDTO signCallBackRequestDTO = new SignCallBackRequestDTO();
        String eversignDocId = CacheUtils.get(BusinessConstantOfERP.EVERSIGN_DOC, SignResponseDTO.class).getData().getEversignDocId();
        signCallBackRequestDTO.setEventHash(eversignDocId);
        //合同相关数据
        EsEventMetaRequest esEventMetaRequest = new EsEventMetaRequest();
        esEventMetaRequest.setRelated_app_id(BusinessConstantOfERP.relatedAppId);
        esEventMetaRequest.setRelated_business_id(BusinessConstantOfERP.relatedBusinessId);
        esEventMetaRequest.setRelated_document_hash(eversignDocId);
        esEventMetaRequest.setRelated_user_id(BusinessConstantOfERP.relatedUserId);
        signCallBackRequestDTO.setEventTime(System.currentTimeMillis()/1000);

        //合同KP相关内容
        EsEventSignerRequest esEventSignerRequest = new EsEventSignerRequest();
        esEventSignerRequest.setEmail(BusinessConstantOfERP.Email);
        esEventSignerRequest.setId("1");
        esEventSignerRequest.setName(BusinessConstantOfERP.KP_NAME);
        esEventSignerRequest.setRole("");
        esEventSignerRequest.setOrder("1");

        signCallBackRequestDTO.setMeta(esEventMetaRequest);
        signCallBackRequestDTO.setSigner(esEventSignerRequest);
        return signCallBackRequestDTO;
    }


}