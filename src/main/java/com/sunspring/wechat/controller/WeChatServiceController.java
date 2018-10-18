package com.sunspring.wechat.controller;

import com.sunspring.wechat.dto.CreateQrcodeParamDTO;
import com.sunspring.wechat.service.WeChatService;
import com.sunspring.wechat.dto.InMessageDTO;
import com.sunspring.wechat.utils.WeChatUtil;
import net.sf.json.JSONObject;
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
    @GetMapping(value = "", produces = "application/text")
    @ResponseBody
    public String judgeToken(String signature, String timestamp, String nonce, String echostr) {
       return weChatService.judgeToken(signature, timestamp, nonce, echostr);
    }

    /**
     * 消息处理
     */
    @PostMapping(value = "", produces = "application/text")
    @ResponseBody
    public String handleMessage(@RequestBody InMessageDTO inMsg) {
       return weChatService.handleMessage(inMsg);
    }

    /**
     * 消息处理
     */
    @PostMapping("/createQrcode")
    @ResponseBody
    public Object createQrcode(@RequestBody CreateQrcodeParamDTO paramDTO) {
       return weChatService.createQrcode(paramDTO);
    }

    /**
     * 通过网页授权回传的code获取用户信息
     * @param code
     * @return
     */
    @GetMapping("/getUserInfo")
    @ResponseBody
    public JSONObject getUserInfo(String code) {
        System.out.println("code>>>>" + code);
        //通过code获取网页授权的accesstoken信息
        JSONObject accesstokenInfo = WeChatUtil.getWebAccessToken(code);
        String webAccesstoken = accesstokenInfo.getString("accesstoken");
        String openId = accesstokenInfo.getString("openId");
        //拉取用户信息
        JSONObject userInfo = WeChatUtil.getWebUserInfo(webAccesstoken, openId);

        return userInfo;
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
