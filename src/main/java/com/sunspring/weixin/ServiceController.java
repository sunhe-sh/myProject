package com.sunspring.weixin;

import com.sunspring.utils.SecurityUtil;
import com.sunspring.weixin.entity.InMessageDTO;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author sunhe
 */
@Controller
@RequestMapping("/wx")
public class ServiceController {
    public static final String TOKEN = "wx_service@sh";

    /**
     * 校验微信URL
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping("")
    @ResponseBody
    public String judgeToken(String signature, String timestamp, String nonce, String echostr) {
        String [] arr = {TOKEN, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String tmp : arr) {
            sb.append(tmp);
        }
        String sha1 = SecurityUtil.sha1(sb.toString());
        System.out.println(sha1);
        if (sha1.equals(signature)) {
            return echostr;
        }else {
            return null;
        }
    }

    @PostMapping("")
    @ResponseBody
    public void handleMessage(@RequestBody InMessageDTO inMsg) {

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
