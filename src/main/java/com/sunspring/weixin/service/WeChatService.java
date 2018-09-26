package com.sunspring.weixin.service;

import com.sunspring.weixin.dto.CreateQrcodeParamDTO;
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
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return String
     */
    String judgeToken(String signature, String timestamp, String nonce, String echostr);

    /**
     * 消息处理
     * @param inMsg 入参
     * @return String
     */
    String handleMessage(InMessageDTO inMsg);

    /**
     * 创建并获取图片Url
     * @param paramDTO
     * @return 二维码图片URL
     */
    Object createQrcode(CreateQrcodeParamDTO paramDTO);
}
