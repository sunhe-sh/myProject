package com.sunspring.activiti.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunhe
 */
public class ScProcessParamsDTO implements Serializable{
    private static final Long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long offeringId;

    /**
     * 商品来源 同业 、异业
     */
    private Integer offeringSource;

    /**
     * 门店id
     */
    private Integer deptId;

    /**
     * 下一节点的审批人
     */
    private String nextFlowUserId;

    /**
     * 当前节点的操作人
     */
    private String curOperationUserId;

    /**
     * 当前节点的操作人名称
     */
    private String curOperationUserName;

    /**
     * 目标用户
     */
    private String targetUserId;

    /**
     * 当前节点操作人角色
     */
    private List<String> curOperationUserRole;

    /**
     * 审核意见
     */
    private String comment;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 流程类型
     * 参见 ProcessTypeEnum 枚举类
     */
    private Integer processType;


    private Integer page;
    private Integer size;
    public Long getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(Long offeringId) {
        this.offeringId = offeringId;
    }

    public Integer getOfferingSource() {
        return offeringSource;
    }

    public void setOfferingSource(Integer offeringSource) {
        this.offeringSource = offeringSource;
    }

    public String getNextFlowUserId() {
        return nextFlowUserId;
    }

    public void setNextFlowUserId(String nextFlowUserId) {
        this.nextFlowUserId = nextFlowUserId;
    }

    public String getCurOperationUserId() {
        return curOperationUserId;
    }

    public void setCurOperationUserId(String curOperationUserId) {
        this.curOperationUserId = curOperationUserId;
    }

    public String getCurOperationUserName() {
        return curOperationUserName;
    }

    public void setCurOperationUserName(String curOperationUserName) {
        this.curOperationUserName = curOperationUserName;
    }

    public List <String> getCurOperationUserRole() {
        return curOperationUserRole;
    }

    public void setCurOperationUserRole(List <String> curOperationUserRole) {
        this.curOperationUserRole = curOperationUserRole;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "ScProcessParamsDTO{" +
                "offeringId=" + offeringId +
                ", offeringSource='" + offeringSource + '\'' +
                ", nextFlowUserId='" + nextFlowUserId + '\'' +
                ", curOperationUserId='" + curOperationUserId + '\'' +
                ", curOperationUserName='" + curOperationUserName + '\'' +
                ", targetUserId='" + targetUserId + '\'' +
                ", curOperationUserRole='" + curOperationUserRole + '\'' +
                ", comment='" + comment + '\'' +
                ", taskId='" + taskId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", processType=" + processType +
                ", page=" + page +
                ", size=" + size +
                ", deptId=" + deptId +
                '}';
    }
}
