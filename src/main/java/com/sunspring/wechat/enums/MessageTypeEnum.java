package com.sunspring.wechat.enums;

/**
* @Description  消息类型
* @Author sunhe
* @Date
**/
public enum MessageTypeEnum {
    //
    TEXT("text","文本"),
    IMAGE("image","图片"),
    VOICE("voice","语音"),
    VIDEO("video","视频"),
    MUSIC("music","音乐"),
    NEWS("news","图文"),
    EVENT("event","事件"),
    LOCATION("location","地理位置消息");

    private String type;
    private String typeDesc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    MessageTypeEnum(String type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static String getTypeDesc(String type){
        MessageTypeEnum[] values = MessageTypeEnum.values();
        for (MessageTypeEnum messageTypeEnum : values) {
            if(messageTypeEnum.getType().equals(type)){
                return messageTypeEnum.getTypeDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }
}
