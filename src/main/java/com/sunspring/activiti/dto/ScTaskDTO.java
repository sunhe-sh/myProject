package com.sunspring.activiti.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunhe
 */
public class ScTaskDTO implements Serializable{
    private static final Long serialVersionUID = 1L;
    /**
     * 任务Id
     */
	private String taskId;
    /**
     * 任务名称
     */
	private String taskName;
    /**
     * 任务节点key
     */
	private String taskDefinitionKey;

    /**
     * 任务发起人
     */
    private String creator;

	/**
	 * 任务受理人
	 */
	private String taskAssignee;

    /**
     * 上一级节点办理人
     */
    private String previousUser;

    /**
     * 任务节点操作类型
     * 参见 OperationTypeEnum 枚举类
     */
    private Integer operationType;

    /**
     * 任务节点操作类型名称
     * 参见 OperationTypeEnum 枚举类
     */
    private String operationTypeName;

    /**
     * 任务开始时间
     */
	private Date taskStartTime;
    /**
     * 任务结束时间
     */
	private Date taskEndTime;
    /**
     * 留言
     */
	private String taskComment;
    /**
     * 流程实例Id
     */
	private String processInstanceId;
    /**
     * 流程是否完成
     * 1.完成
     * 0.未完成
     */
	private Integer finished;
    /**
     * 流程定义key
     */
	private String processDefinitionKey;
    /**
     * 流程类型
     * 参见 ProcessTypeEnum 枚举类
     */
	private Integer processType;

	private String processTypeName;

    /**
     * 任务标题
     */
	private String taskTitle;
    /**
     * 商品id
     */
	private Long offeringId;
    /**
     * 商品类型
     */
	private Integer offeringType;
    /**
     * 商品状态
     */
    private Integer productStatus;

	public ScTaskDTO() {
	}

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTaskAssignee() {
        return taskAssignee;
    }

    public void setTaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }

    public String getPreviousUser() {
        return previousUser;
    }

    public void setPreviousUser(String previousUser) {
        this.previousUser = previousUser;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(String taskComment) {
        this.taskComment = taskComment;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Long getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(Long offeringId) {
        this.offeringId = offeringId;
    }

    public Integer getOfferingType() {
        return offeringType;
    }

    public void setOfferingType(Integer offeringType) {
        this.offeringType = offeringType;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getOperationTypeName() {
        return operationTypeName;
    }

    public void setOperationTypeName(String operationTypeName) {
        this.operationTypeName = operationTypeName;
    }

    public String getProcessTypeName() {
        return processTypeName;
    }

    public void setProcessTypeName(String processTypeName) {
        this.processTypeName = processTypeName;
    }

    @Override
    public String toString() {
        return "ScTaskDTO{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
                ", creator='" + creator + '\'' +
                ", taskAssignee='" + taskAssignee + '\'' +
                ", previousUser='" + previousUser + '\'' +
                ", operationType=" + operationType +
                ", operationTypeName=" + operationTypeName +
                ", taskStartTime=" + taskStartTime +
                ", taskEndTime=" + taskEndTime +
                ", taskComment='" + taskComment + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", finished=" + finished +
                ", processDefinitionKey='" + processDefinitionKey + '\'' +
                ", processType='" + processType + '\'' +
                ", processTypeName='" + processTypeName + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", offeringId=" + offeringId +
                ", offeringType=" + offeringType +
                ", productStatus=" + productStatus +
                '}';
    }
}
