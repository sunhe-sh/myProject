package com.sunspring.weixin.enums;

/**
* @Description  返回值状态码
* @Author sunhe
* @Date
**/
public enum InterfaceCodeEnum {
    //
    SUCCESS("0000","成功"),
    NO_DATA("5000","无数据"),
    ERROR("8888","失败");

    private String code;
    private String codeDesc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    InterfaceCodeEnum(String code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }

    public static String getCodeDesc(String eventKey){
        InterfaceCodeEnum[] values = InterfaceCodeEnum.values();
        for (InterfaceCodeEnum messageTypeEnum : values) {
            if(messageTypeEnum.getCode().equals(eventKey)){
                return messageTypeEnum.getCodeDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

    public static InterfaceCodeEnum getInterfaceCodeEnum(String eventKey){
        InterfaceCodeEnum[] values = InterfaceCodeEnum.values();
        for (InterfaceCodeEnum messageTypeEnum : values) {
            if(messageTypeEnum.getCode().equals(eventKey)){
                return messageTypeEnum;
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

}
