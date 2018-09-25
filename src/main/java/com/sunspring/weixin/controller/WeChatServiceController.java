package com.sunspring.weixin.controller;

import com.sunspring.weixin.service.WeChatService;
import com.sunspring.weixin.utils.JaxbUtil;
import com.sunspring.weixin.utils.SecurityUtil;
import com.sunspring.weixin.utils.WeixinUtil;
import com.sunspring.weixin.dto.ArticleItem;
import com.sunspring.weixin.dto.InMessageDTO;
import com.sunspring.weixin.dto.OutMessageDTO;
import com.sunspring.weixin.enums.EventTypeEnum;
import com.sunspring.weixin.enums.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sunhe
 */
@Controller
@RequestMapping("/wx")
public class WeChatServiceController {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH:mm:ss");

    @Autowired
    private WeChatService weChatService;

    /**
     * 校验微信URL
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return String
     */
    @GetMapping("")
    @ResponseBody
    public String judgeToken(String signature, String timestamp, String nonce, String echostr) {
       return weChatService.judgeToken(signature, timestamp, nonce, echostr);
    }

    /**
     * 消息处理
     * @param inMsg
     * @return String
     */
    @PostMapping("")
    @ResponseBody
    public String handleMessage(@RequestBody InMessageDTO inMsg) {
       return weChatService.handleMessage(inMsg);
    }

    @GetMapping("/test")
    @ResponseBody
    public Map<String, Object> testController() {
        Map<String, Object> resultMap = new HashMap <>();
        resultMap.put("code", 0);
        resultMap.put("message", "request call success!");
        return resultMap;
    }

}
