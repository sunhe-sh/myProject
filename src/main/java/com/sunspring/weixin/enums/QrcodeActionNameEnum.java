package com.sunspring.weixin.enums;

/**
* @Description  事件类型
* @Author sunhe
* @Date
**/
public enum QrcodeActionNameEnum {
    //
    QR_SCENE("QR_SCENE","临时的整型参数值"),
    QR_STR_SCENE("QR_STR_SCENE","临时的字符串参数值"),
    QR_LIMIT_SCENE("QR_LIMIT_SCENE","永久的整型参数值"),
    QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE","永久的字符串参数值");

    private String actionName;
    private String actionNameDesc;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionNameDesc() {
        return actionNameDesc;
    }

    public void setActionNameDesc(String actionNameDesc) {
        this.actionNameDesc = actionNameDesc;
    }

    QrcodeActionNameEnum(String actionName, String actionNameDesc) {
        this.actionName = actionName;
        this.actionNameDesc = actionNameDesc;
    }

    public static String getQrcodeActionNameDesc(String actionName){
        QrcodeActionNameEnum[] values = QrcodeActionNameEnum.values();
        for (QrcodeActionNameEnum messageTypeEnum : values) {
            if(messageTypeEnum.getActionName().equals(actionName)){
                return messageTypeEnum.getActionNameDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

    public static QrcodeActionNameEnum getQrcodeActionNameEnum(String actionName){
        QrcodeActionNameEnum[] values = QrcodeActionNameEnum.values();
        for (QrcodeActionNameEnum messageTypeEnum : values) {
            if(messageTypeEnum.getActionName().equals(actionName)){
                return messageTypeEnum;
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

}
