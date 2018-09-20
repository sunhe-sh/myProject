package com.sunspring.activiti.enums;

/**
* @Description  商品来源
* @Author sunhe
* @Date
**/
public enum GoodsSourceEnum {
    //
    COMMON_SOURCE(10000,"同业商品"),
    DIFF_SOURCE(10001,"异业商品");

    private int value;
    private String typeDesc;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    GoodsSourceEnum(int value, String statusDesc) {
        this.value = value;
        this.typeDesc = statusDesc;
    }

    public static String getTypeDesc(int value){
        GoodsSourceEnum[] values = GoodsSourceEnum.values();
        for (GoodsSourceEnum offeringTypeEnum : values) {
            if(offeringTypeEnum.getValue() == value){
                return offeringTypeEnum.getTypeDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }
}
