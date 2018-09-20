package com.sunspring.activiti.enums;

/**
* @Description  节点操作类型
* @Author sunhe
* @Date
**/
public enum OperationTypeEnum {
    //
    PASS(10001,"通过"),
    REJECT(10000,"驳回"),
    REAPPLY(20001,"重新申请"),
    DISCARD(20000,"放弃申请");

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

    OperationTypeEnum(int value, String statusDesc) {
        this.value = value;
        this.typeDesc = statusDesc;
    }

    public static String getTypeDesc(int value){
        OperationTypeEnum[] values = OperationTypeEnum.values();
        for (OperationTypeEnum offeringTypeEnum : values) {
            if(offeringTypeEnum.getValue() == value){
                return offeringTypeEnum.getTypeDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }
}
