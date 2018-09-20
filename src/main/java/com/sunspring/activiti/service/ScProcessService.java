package com.sunspring.activiti.service;

import com.sunspring.activiti.dto.ScProcessParamsDTO;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author sunhe 2018年8月24日15:42:17
 */
@Service
public interface ScProcessService {

    /**
     * 启动审核流程
     *
     * @param scProcessParamsDTO
     * offeringId
     * processType
     * nextFlowUserId
     * curOperationUserId
     * curOperationUserName
     * curOperationUserRole
     * @return
     */
    Map<String,Object> applyProcess(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 审批通过
     * @param scProcessParamsDTO
     * taskId
     * nextFlowUserId
     * curOperationUserRole
     * comment
     * @return
     */
    Map<String,Object> approvalProcess(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 审批驳回
     * @param scProcessParamsDTO
     * taskId
     * comment
     * curOperationUserRole
     * @return
     */
    Map<String,Object> rejectProcess(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 流程驳回后，重新申请
     * @param scProcessParamsDTO
     * taskId
     * nextFlowUserId
     * comment
     * @return
     */
    Map<String,Object> reapplyProcess(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 流程驳回后，放弃申请
     * @param scProcessParamsDTO
     * taskId
     * @return
     */
    Map<String,Object> discardProcess(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 获取待办任务列表
     * @param scProcessParamsDTO
     * page
     * size
     * curOperationUserId
     * @return
     */
    Map<String,Object> getTodoTaskListByPage(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 获取已办任务列表
     * @param scProcessParamsDTO
     * page
     * size
     * curOperationUserId
     * @return
     */
    Map<String, Object> getHasDoneTaskListByPage(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 获取审批流程活动列表
     * @param scProcessParamsDTO
     * processInstanceId
     * @return
     */
    Map<String, Object> getApprovalRecord(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 获取流程变量
     * @param taskId
     * @return
     */
    Map<String,Object> getVariables(String taskId);

    /**
     * 任务转派
     * @param scProcessParamsDTO
     * taskId
     * targetUserId
     * @return
     */
    Map<String, Object> assignTask(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 获取流程下个节点（下个角色）
     * processType
     * curOperationUserRole
     * @param scProcessParamsDTO
     * @return
     */
    Map<String, Object> getNextNodes(ScProcessParamsDTO scProcessParamsDTO);

    /**
     * 强制删除流程定义
     * @param deploymentId
     * @return
     */
    Map<String,Object> deleteProcessDefinition(String deploymentId);

}