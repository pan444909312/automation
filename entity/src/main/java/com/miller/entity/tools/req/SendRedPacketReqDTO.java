package com.miller.entity.tools.req;

import lombok.Data;

/**
 * @Author: panjuxiang
 * @Since: 2025/6/18
 */
@Data
public class SendRedPacketReqDTO {
    private Long userId;
    private Long redPacketId;
}
