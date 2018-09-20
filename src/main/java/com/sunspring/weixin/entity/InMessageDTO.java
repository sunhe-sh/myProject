package com.sunspring.weixin.entity;

public class InMessageDTO {


    private String ToUserName;	        //开发者微信号
    private String FromUserName;	    //发送方帐号（一个OpenID）
    private Long CreateTime;	        //消息创建时间 （整型）
    private String MsgType;             //text、image
    private String Content;	            //文本消息内容
    private Long MsgId;	                //消息id，64位整型

    private String PicUrl;	            //图片链接（由系统生成）
    private String MediaId;	            //图片消息媒体id，可以调用多媒体文件下载接口拉取数据。

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Long getMsgId() {
        return MsgId;
    }

    public void setMsgId(Long msgId) {
        MsgId = msgId;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
