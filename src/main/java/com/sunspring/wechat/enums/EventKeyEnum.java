package com.sunspring.wechat.enums;

/**
* @Description  事件key类型
* @Author sunhe
* @Date
**/
public enum EventKeyEnum {
    //
    SCAN_APPOINTMENT("scanAppointment","扫码取号");

    private String eventKey;
    private String eventKeyDesc;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getEventKeyDesc() {
        return eventKeyDesc;
    }

    public void setEventKeyDesc(String eventKeyDesc) {
        this.eventKeyDesc = eventKeyDesc;
    }

    EventKeyEnum(String eventKey, String eventKeyDesc) {
        this.eventKey = eventKey;
        this.eventKeyDesc = eventKeyDesc;
    }

    public static String getTypeDesc(String eventKey){
        EventKeyEnum[] values = EventKeyEnum.values();
        for (EventKeyEnum messageTypeEnum : values) {
            if(messageTypeEnum.getEventKey().equals(eventKey)){
                return messageTypeEnum.getEventKeyDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

    public static EventKeyEnum getEventTypeEnum(String eventKey){
        EventKeyEnum[] values = EventKeyEnum.values();
        for (EventKeyEnum messageTypeEnum : values) {
            if(messageTypeEnum.getEventKey().equals(eventKey)){
                return messageTypeEnum;
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

}
