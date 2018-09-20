package com.sunspring.activiti.controller;

import com.sunspring.activiti.dto.ScProcessParamsDTO;
import com.sunspring.activiti.enums.InterfaceCodeEnum;
import com.sunspring.activiti.service.ScProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunhe
 */

@Controller
@RequestMapping(value = "/v1/process")
public class ScProcessController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScProcessService scProcessService;

    /**
     *  启动审核流程
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/applyProcess")
    @ResponseBody
    public Map applyShelf(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("启动审核流程:applyProcess 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.applyProcess(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("启动审核流程:applyProcess 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("启动审核流程:applyProcess 返回结果:{}"+resultMap.toString());
        return resultMap;
    }
    /**
     *  任务节点审批通过
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/approvalProcess")
    @ResponseBody
    public Map approvalProcess(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("任务节点审批通过:approvalProcess 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.approvalProcess(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("任务节点审批通过:approvalProcess 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("任务节点审批通过:approvalProcess 返回结果:{}"+resultMap.toString());
        return resultMap;
    }
    /**
     *  任务节点审批驳回
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/rejectProcess")
    @ResponseBody
    public Map rejectProcess(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("任务节点审批驳回:rejectProcess 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.rejectProcess(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("任务节点审批驳回:rejectProcess 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("任务节点审批驳回:rejectProcess 返回结果:{}"+resultMap.toString());
        return resultMap;
    }
    /**
     * 流程驳回后，重新申请
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/reapplyProcess")
    @ResponseBody
    public Map reapplyProcess(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("重新申请:reapplyProcess 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.reapplyProcess(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("重新申请:reapplyProcess 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("重新申请:reapplyProcess 返回结果:{}"+resultMap.toString());
        return resultMap;
    }
    /**
     * 流程驳回后，放弃申请
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/discardProcess")
    @ResponseBody
    public Map discardProcess(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("放弃申请:discardProcess 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.discardProcess(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("放弃申请:discardProcess 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("放弃申请:discardProcess 返回结果:{}"+resultMap.toString());
        return resultMap;
    }
    /**
     * 获取待办任务列表
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/getTodoTaskListByPage")
    @ResponseBody
    public Map getTodoTaskListByPage(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("获取待办任务列表:getTodoTaskListByPage 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.getTodoTaskListByPage(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("获取待办任务列表:getTodoTaskListByPage 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("获取待办任务列表:getTodoTaskListByPage 返回结果:{}"+resultMap.toString());
        return resultMap;
    }
    /**
     * 获取已办任务列表
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/getHasDoneTaskListByPage")
    @ResponseBody
    public Map getHasDoneTaskListByPage(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("获取已办任务列表:getHasDoneTaskListByPage 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.getHasDoneTaskListByPage(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("获取已办任务列表:getHasDoneTaskListByPage 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("获取已办任务列表:getHasDoneTaskListByPage 返回结果:{}"+resultMap.toString());
        return resultMap;
    }

    /**
     * 获取审批流程活动列表
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/getApprovalRecord")
    @ResponseBody
    public Map getApprovalRecord(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("获取审批流程活动列表:getApprovalRecord 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.getApprovalRecord(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("获取审批流程活动列表:getApprovalRecord 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("获取审批流程活动列表:getApprovalRecord 返回结果:{}"+resultMap.toString());
        return resultMap;
    }
    /**
     * 任务转派
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/assignTask")
    @ResponseBody
    public Map assignTask(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("任务转派:assignTask 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.assignTask(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("任务转派:assignTask 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("任务转派:assignTask 返回结果:{}"+resultMap.toString());
        return resultMap;
    }
    /**
     * 获取流程下个节点（下个角色）
     * @param scProcessParamsDTO 参数
     * @return Map
     */
    @PostMapping(value = "/getNextNodes")
    @ResponseBody
    public Map getNextNodes(@RequestBody ScProcessParamsDTO scProcessParamsDTO) {
        logger.info("获取流程下个节点:getNextNodes 参数: ", scProcessParamsDTO.toString());
        Map<String,Object> resultMap = new HashMap <>(16);
        try {
            resultMap = scProcessService.getNextNodes(scProcessParamsDTO);
        } catch (Exception e) {
            logger.error("获取流程下个节点:getNextNodes 异常信息:{}", e);
            e.printStackTrace();
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
        logger.info("获取流程下个节点:getNextNodes 返回结果:{}"+resultMap.toString());
        return resultMap;
    }
    /**
     * 获取流程变量
     * @param taskId 任务ID
     * @return Map
     */
    @GetMapping("/getVariables")
    @ResponseBody
    public Map getVariables(@RequestParam String taskId){
        return scProcessService.getVariables(taskId);
    }
    /**
     * 删除流程定义
     * @param deploymentId 流程装载ID
     * @return  Map
     */
    @GetMapping("/deleteProcessDefinition")
    @ResponseBody
    public Map deleteProcessDefinition(@RequestParam String deploymentId){
        return scProcessService.deleteProcessDefinition(deploymentId);
    }

}
