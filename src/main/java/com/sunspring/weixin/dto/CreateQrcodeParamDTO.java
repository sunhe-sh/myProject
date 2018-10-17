package com.sunspring.weixin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunhe
 */
@Getter
@Setter
public class CreateQrcodeParamDTO {

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
     */
    private Long expire_seconds;
    /**
     * 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
     */
    private String action_name;
    /**
     * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     */
    private String scene_str;
    /**
     * 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     */
    private Long scene_id;

    @Override
    public String toString() {
        return "{" +
                "    \"expire_seconds\": " + expire_seconds + "," +
                "    \"action_name\": \"" + action_name + "\"," +
                "    \"action_info\": {" +
                "        \"scene\": {" +
                "            \"scene_id\": " + scene_id + "," +
                "            \"scene_str\": \"" + scene_str +"\"" +
                "        }" +
                "    }" +
                "}";
    }
}
