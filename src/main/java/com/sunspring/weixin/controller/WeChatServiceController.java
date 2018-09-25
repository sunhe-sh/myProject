package com.sunspring.weixin.controller;

import com.sunspring.weixin.dto.CreateQrcodeParamDTO;
import com.sunspring.weixin.service.WeChatService;
import com.sunspring.weixin.dto.InMessageDTO;
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
     */
    @GetMapping("")
    @ResponseBody
    public String judgeToken(String signature, String timestamp, String nonce, String echostr) {
       return weChatService.judgeToken(signature, timestamp, nonce, echostr);
    }

    /**
     * 消息处理
     */
    @PostMapping("")
    @ResponseBody
    public String handleMessage(@RequestBody InMessageDTO inMsg) {
       return weChatService.handleMessage(inMsg);
    }

    /**
     * 消息处理
     */
    @PostMapping("/createQrcode")
    @ResponseBody
    public String createQrcode(@RequestBody CreateQrcodeParamDTO paramDTO) {
       return weChatService.createQrcode(paramDTO);
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
