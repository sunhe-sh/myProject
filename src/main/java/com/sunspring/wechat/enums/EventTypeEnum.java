package com.sunspring.wechat.enums;

/**
* @Description  事件类型
* @Author sunhe
* @Date
**/
public enum EventTypeEnum {
    //
    SUBSCRIBE("subscribe","关注事件"),
    UNSUBSCRIBE("unsubscribe","取消关注事件"),
    SCAN("SCAN","二维码扫码（用户已关注时的事件推送）"),
    LOCATION("LOCATION","上报地理位置事件"),
    CLICK("CLICK","点击菜单拉取消息时的事件"),
    SCANCODE_PUSH("scancode_push","点击菜单扫码时的事件"),
    LOCATION_SELECT("location_select","点击菜单地理位置选择器时的事件"),
    TEMPLATE_SEND_JOB_FINISH("TEMPLATESENDJOBFINISH","模板消息发送完成"),
    VIEW("VIEW","点击菜单跳转链接时的事件");

    private String event;
    private String eventDesc;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    EventTypeEnum(String event, String eventDesc) {
        this.event = event;
        this.eventDesc = eventDesc;
    }

    public static String getTypeDesc(String event){
        EventTypeEnum[] values = EventTypeEnum.values();
        for (EventTypeEnum messageTypeEnum : values) {
            if(messageTypeEnum.getEvent().equals(event)){
                return messageTypeEnum.getEventDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

    public static EventTypeEnum getEventTypeEnum(String event){
        EventTypeEnum[] values = EventTypeEnum.values();
        for (EventTypeEnum messageTypeEnum : values) {
            if(messageTypeEnum.getEvent().equals(event)){
                return messageTypeEnum;
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

}
