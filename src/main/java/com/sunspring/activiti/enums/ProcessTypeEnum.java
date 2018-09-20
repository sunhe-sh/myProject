package com.sunspring.activiti.enums;

/**
* @Description  流程类型
* @Author sunhe
* @Date
**/
public enum ProcessTypeEnum {
    /**
     * 上架流程 对外、流程定义key、流程名称
     */
    SHELF_PROCESS(10001,"shelfProcess", "上架审批流程"),
    /**
     * 下架流程 对外、流程定义key、流程名称
     */
    UNSHELF_PROCESS(10002,"unshelfProcess", "下架审批流程");
    private int value;
    private String type;
    private String typeDesc;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

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

    ProcessTypeEnum(int value, String type, String typeDesc) {
        this.value = value;
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static String getType(int value){
        ProcessTypeEnum[] values = ProcessTypeEnum.values();
        for (ProcessTypeEnum processTypeEnum : values) {
            if(processTypeEnum.getValue() == value){
                return processTypeEnum.getType();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }
    public static String getTypeDesc(int value){
        ProcessTypeEnum[] values = ProcessTypeEnum.values();
        for (ProcessTypeEnum processTypeEnum : values) {
            if(processTypeEnum.getValue() == value){
                return processTypeEnum.getTypeDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }
    public static String getTypeDesc(String type){
        ProcessTypeEnum[] values = ProcessTypeEnum.values();
        for (ProcessTypeEnum processTypeEnum : values) {
            if(ProcessTypeEnum.getType(processTypeEnum.getValue()).equals(type)){
                return processTypeEnum.getTypeDesc();
            }
        }
        throw new IllegalArgumentException("没有找到对应的数据！");
    }
}
