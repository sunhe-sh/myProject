package com.sunspring.weixin.service;

import com.sunspring.weixin.dto.InMessageDTO;
import org.springframework.stereotype.Service;

/**
 * @author sunhe
 */
@Service
public interface WeChatService {

    /**
     * 校验微信URL
     * 1）将token、timestamp、nonce三个参数进行字典序排序
     * 2）将三个参数字符串拼接成一个字符串进行sha1加密
     * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return String
     */
    String judgeToken(String signature, String timestamp, String nonce, String echostr);

    /**
     * 消息处理
     * @param inMsg
     * @return String
     */
    String handleMessage(InMessageDTO inMsg);

}
