package com.sunspring.weixin;

import com.sunspring.weixin.utils.JaxbUtil;
import com.sunspring.weixin.utils.SecurityUtil;
import com.sunspring.weixin.utils.WeixinUtil;
import com.sunspring.weixin.dto.ArticleItem;
import com.sunspring.weixin.dto.InMessageDTO;
import com.sunspring.weixin.dto.OutMessageDTO;
import com.sunspring.weixin.enums.EventTypeEnum;
import com.sunspring.weixin.enums.MessageTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sunhe
 */
@Controller
@RequestMapping("/wx")
public class ServiceController {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH:mm:ss");
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
    @GetMapping("")
    @ResponseBody
    public String judgeToken(String signature, String timestamp, String nonce, String echostr) {
        String [] arr = {WeixinUtil.TOKEN, timestamp, nonce};
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

    /**
     * 消息处理
     * @param inMsg
     * @return Object
     */
    @PostMapping("")
    @ResponseBody
    public Object handleMessage(@RequestBody InMessageDTO inMsg) {

        System.out.println(inMsg);
        OutMessageDTO outMsg = new OutMessageDTO();
        //设置发送方
        outMsg.setFromUserName(inMsg.getToUserName());
        //设置接收方
        outMsg.setToUserName(inMsg.getFromUserName());
        //时间
        outMsg.setCreateTime(System.currentTimeMillis());
        //消息类型
        String msgType = inMsg.getMsgType();
        if(MessageTypeEnum.TEXT.getType().equals(msgType)){
            outMsg.setMsgType(MessageTypeEnum.TEXT.getType());
            outMsg.setContent(keyWordsResponse(inMsg.getContent()));
        } else if(MessageTypeEnum.IMAGE.getType().equals(msgType)) {
            outMsg.setMsgType(MessageTypeEnum.IMAGE.getType());
            String [] mediaId = {inMsg.getMediaId()};
            outMsg.setMediaId(mediaId);
        } else if(MessageTypeEnum.EVENT.getType().equals(msgType)) {
            //处理事件
            switch (EventTypeEnum.getEventTypeEnum(inMsg.getEvent())) {
                case SUBSCRIBE:
                    outMsg.setMsgType(MessageTypeEnum.TEXT.getType());
                    subscribeEventHandler(inMsg, outMsg);
                    break;
                case UNSUBSCRIBE:
                    break;
                case SCAN:
                    break;
                case LOCATION:
                    break;
                case CLICK:
                    clickEventHandler(inMsg, outMsg);
                    break;
                case VIEW:
                    break;
                case SCANCODE_PUSH:
                    scancodePushEventHandler(inMsg, outMsg);
                    break;
                case LOCATION_SELECT:
                    break;
                default:
                    break;
            }
        }
        System.out.println(JaxbUtil.convertToXml(outMsg));
        return JaxbUtil.convertToXml(outMsg);
    }

    /**
     * 处理二维码扫描类型按钮事件
     * @param inMsg
     * @param outMsg
     */
    private void scancodePushEventHandler(InMessageDTO inMsg, OutMessageDTO outMsg) {
        outMsg.setMsgType(MessageTypeEnum.TEXT.getType());
        outMsg.setContent("扫码");
    }

    private String keyWordsResponse(String inMsg){
        String outMsg = null;
        if(inMsg.contains("时间")){
            outMsg = sdf.format(new Date());
        } else {
            outMsg = inMsg;
        }
        return outMsg;
    }

    /**
     * 处理关注事件
     * @param inMsg
     * @param outMsg
     */
    private void subscribeEventHandler(InMessageDTO inMsg, OutMessageDTO outMsg){
//        responseText4SubscribeEvent(inMsg, outMsg);
        responseNews4SubscribeEvent(inMsg, outMsg);
    }

    /**
     * 回复图文消息 for 关注事件
     * @param inMsg
     * @param outMsg
     */
    private void responseNews4SubscribeEvent(InMessageDTO inMsg, OutMessageDTO outMsg) {
        List<ArticleItem> articleItemList = new ArrayList <>();
        ArticleItem articleItem = new ArticleItem();
        articleItem.setTitle("微信公众号快速开发教程");
        articleItem.setDescription("本课程针对微信公众号实现验证接入，消息推送，处理文本，图片，事件，图文消息等类型，实现关注时回复，普通回复，根据关键字回复，自定义菜单的创建与删除，网页授权获取微信用户信息，利用微信JSSDK监听分享朋友圈事件。此外，本课程提供了与课程同步的技术文章，视频与文章结合，以便同学更好地理解。文章请进群索取。");
        articleItem.setPicUrl("https://10.url.cn/qqcourse_logo_ng/ajNVdqHZLLAMtLTXv8U5JicV2erR1rtc793gOel7r7ibwqRFn7jWObO7yDpfshYzNtOb3aev9Gwlk/356");
        articleItem.setUrl("https://ke.qq.com/course/293104");
        articleItemList.add(articleItem);
        outMsg.setArticleCount(1);
        outMsg.setMsgType(MessageTypeEnum.NEWS.getType());
        outMsg.setItem(articleItemList);
    }

    /**
     * 回复文本消息 for 关注事件
     * @param inMsg
     * @param outMsg
     */
    private void responseText4SubscribeEvent(InMessageDTO inMsg, OutMessageDTO outMsg) {
        String outSb = "欢迎关注！！" +
                "\n" +
                "[坏笑]" +
                "\n" +
                "当前时间：" + sdf.format(new Date());
        outMsg.setContent(outSb);
    }

    /**
     * 处理菜单点击事件
     * @param inMsg
     * @param outMsg
     */
    private void clickEventHandler(InMessageDTO inMsg, OutMessageDTO outMsg){
        if("V1001_CURRENT_TIME".equals(inMsg.getEventKey())){
            outMsg.setContent(sdf.format(new Date()));
            outMsg.setMsgType(MessageTypeEnum.TEXT.getType());
        }
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
