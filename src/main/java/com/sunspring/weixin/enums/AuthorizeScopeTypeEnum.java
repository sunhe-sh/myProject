package com.sunspring.weixin.enums;

/**
* @Description  应用授权作用域
* @Author sunhe
* @Date
**/
public enum AuthorizeScopeTypeEnum {
    //
    SNSAPI_BASE("snsapi_base","不弹出授权页面，直接跳转，只能获取用户openid"),
    SNSAPI_USERINFO("snsapi_userinfo","弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息");

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

    AuthorizeScopeTypeEnum(String type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static String getQrcodeActionNameDesc(String type){
        AuthorizeScopeTypeEnum[] values = AuthorizeScopeTypeEnum.values();
        for (AuthorizeScopeTypeEnum messageTypeEnum : values) {
            if(messageTypeEnum.getType().equals(type)){
                return messageTypeEnum.getTypeDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

    public static AuthorizeScopeTypeEnum getQrcodeActionNameEnum(String type){
        AuthorizeScopeTypeEnum[] values = AuthorizeScopeTypeEnum.values();
        for (AuthorizeScopeTypeEnum messageTypeEnum : values) {
            if(messageTypeEnum.getType().equals(type)){
                return messageTypeEnum;
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }

}
