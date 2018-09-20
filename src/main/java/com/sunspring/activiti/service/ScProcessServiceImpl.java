package com.sunspring.activiti.service;

import com.sunspring.activiti.dto.ScProcessParamsDTO;
import com.sunspring.activiti.dto.ScTaskDTO;
import com.sunspring.activiti.enums.InterfaceCodeEnum;
import com.sunspring.activiti.enums.OperationTypeEnum;
import com.sunspring.activiti.enums.ProcessTypeEnum;
import com.sunspring.activiti.utils.PageList;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author sunhe on 2018/8/26.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScProcessServiceImpl implements ScProcessService {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private HistoryService historyService;

    @Override
    public Map <String, Object> applyProcess(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String,Object> resultMap = new HashMap <>();
        resultMap = judgeApplyProcessParam(scProcessParamsDTO, resultMap);
        if(resultMap.get("code") != InterfaceCodeEnum.SUCCESS){
            return resultMap;
        }
        Long offeringId = (Long)resultMap.get("offeringId");
        String offeringName = (String)resultMap.get("offeringName");
        Object offeringObject = resultMap.get("prodOffering");
        //工作流相关逻辑 启动流程
        ProcessDefinition processDefinition = (ProcessDefinition) resultMap.get("processDefinition");
        ProcessInstance processInstance = startProcess(offeringId, offeringName, scProcessParamsDTO, processDefinition);
        resultMap = completeWriteInfoTask(processInstance,scProcessParamsDTO);
        if(resultMap.get("code") != InterfaceCodeEnum.SUCCESS){
            return resultMap;
        }
        //处理商品相关逻辑
        handleOfferingProcessLogic(scProcessParamsDTO, offeringObject, processInstance);

        return resultMap;
    }

    private void handleOfferingProcessLogic(ScProcessParamsDTO scProcessParamsDTO, Object offeringObject, ProcessInstance processInstance) {
        Integer processType = scProcessParamsDTO.getProcessType();
        Integer offeringSource = scProcessParamsDTO.getOfferingSource();
        //上架流程
        if(processType == ProcessTypeEnum.SHELF_PROCESS.getValue()){

        }
        //下架流程
        else if (processType == ProcessTypeEnum.UNSHELF_PROCESS.getValue()){

        }
    }

    private Map <String, Object> judgeApplyProcessParam(ScProcessParamsDTO scProcessParamsDTO, Map<String,Object> resultMap) {
        Long offeringId = scProcessParamsDTO.getOfferingId();
        if(offeringId == null){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "商品ID不能为空");
            return resultMap;
        }

        if(StringUtils.isEmpty(scProcessParamsDTO.getNextFlowUserId())){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未选择下个任务节点的审批人！");
            return resultMap;
        }
        if(StringUtils.isEmpty(scProcessParamsDTO.getCurOperationUserId())){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未传入当前操作用户！");
            return resultMap;
        }
        if(scProcessParamsDTO.getCurOperationUserRole().size() == 0){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未传入当前操作用户角色！");
            return resultMap;
        }
        if(scProcessParamsDTO.getOfferingSource() == null){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未传入商品来源！");
            return resultMap;
        }
        if(scProcessParamsDTO.getProcessType() == null){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未传入流程类型！");
            return resultMap;
        }
        //处理工作流相关逻辑
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(ProcessTypeEnum.getType(scProcessParamsDTO.getProcessType()))
                .latestVersion().singleResult();
        if(processDefinition == null){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未找到对应审批流程！");
            return resultMap;
        }
        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("processDefinition", processDefinition);
        return resultMap;
    }

    private ProcessInstance startProcess(Long offeringId, String offeringName, ScProcessParamsDTO scProcessParamsDTO, ProcessDefinition processDefinition) {
        Map<String, Object> map = new HashMap<>();
        String processName = scProcessParamsDTO.getCurOperationUserName() + "的" + processDefinition.getName() + "："
                +"（"+ offeringId + "）"+offeringName;
        map.put("creator", scProcessParamsDTO.getCurOperationUserId());
        map.put("creatorRole", scProcessParamsDTO.getCurOperationUserRole());
        map.put("curRole", scProcessParamsDTO.getCurOperationUserRole());
        map.put("processName", processName);
        map.put("processType", scProcessParamsDTO.getProcessType());
        map.put("offeringSource", scProcessParamsDTO.getOfferingSource());
        map.put("deptId", scProcessParamsDTO.getDeptId());
        map.put("offeringId", offeringId);
        map.put("prevUser", scProcessParamsDTO.getCurOperationUserId());
        map.put("assignee", scProcessParamsDTO.getCurOperationUserId());
        String businessKey = ProcessTypeEnum.getType(scProcessParamsDTO.getProcessType())+":"+offeringId;
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(ProcessTypeEnum.getType(scProcessParamsDTO.getProcessType()),businessKey, map);
        return processInstance;
    }

    /**
     * 流程填写信息节点提交
     * @param processInstance
     * @param scProcessParamsDTO
     * @return
     */
    private Map<String,Object> completeWriteInfoTask(ProcessInstance processInstance, ScProcessParamsDTO scProcessParamsDTO) {
        String processId = processInstance.getId();
        String taskId = taskService.createTaskQuery().processInstanceId(processId).singleResult().getId();
        scProcessParamsDTO.setTaskId(taskId);
        return approvalProcess(scProcessParamsDTO);
    }

    @Override
    public Map<String,Object> approvalProcess(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String,Object> resultMap = new HashMap <>();
        resultMap = judgeApprovalProcessParam(scProcessParamsDTO, resultMap);
        if(resultMap.get("code") != InterfaceCodeEnum.SUCCESS){
            return resultMap;
        }
        String taskId = scProcessParamsDTO.getTaskId();
        Task task = (Task) resultMap.get("task");
        Map<String,Object> variables = new HashMap<>();
        Map<String,Object> variablesLocal = new HashMap<>();
        variables.put("operationType", OperationTypeEnum.PASS.getValue());
        variables.put("prevUser", task.getAssignee());
        variables.put("comment",scProcessParamsDTO.getComment());
        variables.put("assignee", scProcessParamsDTO.getNextFlowUserId());
        variables.put("curRole", scProcessParamsDTO.getCurOperationUserRole());

        variablesLocal.put("prevUser", task.getAssignee());
        variablesLocal.put("operationType", OperationTypeEnum.PASS.getValue());
        variablesLocal.put("comment",scProcessParamsDTO.getComment());
        taskService.setVariablesLocal(taskId, variablesLocal);
        taskService.complete(taskId,variables);

        resultMap.clear();
        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "操作成功！");
        return resultMap;
    }
    private Map<String, Object> judgeApprovalProcessParam(ScProcessParamsDTO scProcessParamsDTO, Map<String,Object> resultMap) {
        String taskId = scProcessParamsDTO.getTaskId();
        if(StringUtils.isEmpty(taskId)){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少任务ID");
            return resultMap;
        }
        if(StringUtils.isEmpty(scProcessParamsDTO.getNextFlowUserId())){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少下一节点用户ID");
            return resultMap;
        }
        List<String> roles = scProcessParamsDTO.getCurOperationUserRole();
        if(Objects.isNull(roles) || roles.size() == 0){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少当前用户角色");
            return resultMap;
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未查询到当前任务信息！");
            return resultMap;
        }
        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("task", task);
        return resultMap;
    }

    @Override
    public Map<String,Object> rejectProcess(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String,Object> resultMap = new HashMap <>();
        String taskId = scProcessParamsDTO.getTaskId();
        if(StringUtils.isEmpty(taskId)){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少任务ID");
            return resultMap;
        }
        if(scProcessParamsDTO.getCurOperationUserRole().size() == 0){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少当前用户角色");
            return resultMap;
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未查询到当前任务信息！");
            return resultMap;
        }
        String creator = (String)taskService.getVariable(taskId, "creator");
        Map<String,Object> variables = new HashMap<>();
        Map<String,Object> variablesLocal = new HashMap<>();
        variables.put("operationType", OperationTypeEnum.REJECT.getValue());
        variables.put("prevUser", task.getAssignee());
        variables.put("comment",scProcessParamsDTO.getComment());
        variables.put("assignee", creator);
        variables.put("curRole", scProcessParamsDTO.getCurOperationUserRole());

        variablesLocal.put("prevUser", task.getAssignee());
        variablesLocal.put("operationType", OperationTypeEnum.REJECT.getValue());
        variablesLocal.put("comment",scProcessParamsDTO.getComment());
        taskService.setVariablesLocal(taskId, variablesLocal);
        taskService.complete(taskId, variables);

        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "操作成功！");
        return resultMap;
    }
    @Override
    public Map<String,Object> reapplyProcess(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String,Object> resultMap = new HashMap <>();
        String taskId = scProcessParamsDTO.getTaskId();
        if(StringUtils.isEmpty(taskId)){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少任务ID");
            return resultMap;
        }
        if(StringUtils.isEmpty(scProcessParamsDTO.getNextFlowUserId())){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少下一节点用户ID");
            return resultMap;
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未查询到当前任务信息！");
            return resultMap;
        }
        List<String> creatorRole = (List <String>) taskService.getVariable(taskId, "creatorRole");
        Map<String,Object> variables = new HashMap<>();
        Map<String,Object> variablesLocal = new HashMap<>();
        variables.put("operationType", OperationTypeEnum.REAPPLY.getValue());
        variables.put("prevUser", task.getAssignee());
        variables.put("comment",scProcessParamsDTO.getComment());
        variables.put("assignee", scProcessParamsDTO.getNextFlowUserId());
        variables.put("curRole", creatorRole);

        variablesLocal.put("prevUser", task.getAssignee());
        variablesLocal.put("operationType", OperationTypeEnum.REAPPLY.getValue());
        variablesLocal.put("comment",scProcessParamsDTO.getComment());
        taskService.setVariablesLocal(taskId, variablesLocal);
        taskService.complete(taskId, variables);

        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "操作成功！");
        return resultMap;
    }
    @Override
    public Map<String,Object> discardProcess(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String,Object> resultMap = new HashMap <>();
        String taskId = scProcessParamsDTO.getTaskId();
        if(StringUtils.isEmpty(taskId)){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少任务ID");
            return resultMap;
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "未查询到当前任务信息！");
            return resultMap;
        }
        Map<String,Object> variables = new HashMap<>();
        Map<String,Object> variablesLocal = new HashMap<>();
        variables.put("operationType", OperationTypeEnum.DISCARD.getValue());
        variables.put("comment",scProcessParamsDTO.getComment());

        variablesLocal.put("prevUser", task.getAssignee());
        variablesLocal.put("operationType", OperationTypeEnum.DISCARD.getValue());
        variablesLocal.put("comment",scProcessParamsDTO.getComment());
        taskService.setVariablesLocal(taskId, variablesLocal);
        taskService.complete(taskId, variables);

        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "操作成功！");
        return resultMap;
    }

    @Override
    public Map<String,Object> getTodoTaskListByPage(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String, Object> resultMap = new HashMap <>();
        String currentUserId = scProcessParamsDTO.getCurOperationUserId();
        if(StringUtils.isEmpty(currentUserId)) {
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少当前操作用户ID");
            resultMap.put("data", null);
            return resultMap;
        }
        List<ScTaskDTO> taskDTOList = new ArrayList<ScTaskDTO>();

        Integer page = scProcessParamsDTO.getPage();
        if(page == null){
            page = 1;
        }
        Integer size = scProcessParamsDTO.getSize();
        if(size == null){
            size = 10;
        }
        //与正在执行的任务管理相关的Service
        long count = taskService
                //创建任务查询对象
                .createTaskQuery()
                //指定个人任务查询，指定办理人
                .taskAssignee(currentUserId)
                .count();
        if(count==0){
            resultMap.put("code", InterfaceCodeEnum.SUCCESS);
            resultMap.put("message", "查询成功！");
            resultMap.put("data", PageList.success(size,page,(int)count,taskDTOList));
            return resultMap;
        }
        Integer startPosition = (page - 1) * size;
        //与正在执行的任务管理相关的Service
        List<Task> list = taskService
                //创建任务查询对象
                .createTaskQuery()
                //指定个人任务查询，指定办理人
                .taskAssignee(currentUserId)
                //组任务的办理人查询
                //.taskCandidateUser(candidateUser)
                //排序
                //使用创建时间的升序排列
                .orderByTaskCreateTime().desc()
                //**返回结果集
                .listPage(startPosition,size);
        ScTaskDTO taskDTO;
        String processInstanceId;
        ProcessInstance processInstance;
        String processDefinitionKey;
        Long offeringId;
        Integer processType;
        String processName;
        String creator;
        String prevUser;
        Map<String, Object> variables = new HashMap <>();
        for (Task task : list) {
            taskDTO = new ScTaskDTO();
            variables = taskService.getVariables(task.getId());
            processInstanceId = task.getProcessInstanceId();
            // 根据流程实例id获取流程实例
            processInstance=runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            processDefinitionKey = processInstance.getProcessDefinitionKey();
            taskDTO.setProcessDefinitionKey(processDefinitionKey);
            creator = (String)variables.get("creator");
            prevUser = (String)variables.get("prevUser");
            processName = (String)variables.get("processName");
            taskDTO.setTaskTitle("（"+task.getName()+"）"+processName);
            taskDTO.setCreator(creator);
            taskDTO.setPreviousUser(prevUser);
            offeringId = (Long)variables.get("offeringId");
            processType = (Integer)variables.get("processType");

            taskDTO.setProcessType(processType);
            taskDTO.setProcessTypeName(ProcessTypeEnum.getTypeDesc(processType));
            taskDTO.setProcessInstanceId(processInstanceId);
            taskDTO.setTaskId(task.getId());
            taskDTO.setTaskName(task.getName());
            taskDTO.setTaskAssignee(task.getAssignee());
            taskDTO.setTaskStartTime(task.getCreateTime());
            taskDTO.setTaskDefinitionKey(task.getTaskDefinitionKey());
            taskDTO.setFinished(0);
            taskDTOList.add(taskDTO);
        }
        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "查询成功！");
        resultMap.put("data", PageList.success(size,page,(int)count,taskDTOList));
        return resultMap;
    }

    @Override
    public Map<String, Object> getHasDoneTaskListByPage(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String, Object> resultMap = new HashMap <>();
        String currentUserId = scProcessParamsDTO.getCurOperationUserId();
        if(StringUtils.isEmpty(currentUserId)) {
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少当前操作用户ID");
            resultMap.put("data", null);
            return resultMap;
        }
        // 历史任务Service
        long count = historyService
                .createHistoricTaskInstanceQuery()
                .taskAssignee(currentUserId)
                .finished()
                .count();
        Integer page = scProcessParamsDTO.getPage();
        if(page == null){
            page = 1;
        }
        Integer size = scProcessParamsDTO.getSize();
        if(size == null){
            size = 10;
        }
        List<ScTaskDTO> taskDTOList = new ArrayList<>();
        if(count==0){
            resultMap.put("code", InterfaceCodeEnum.SUCCESS);
            resultMap.put("message", "查询成功！");
            resultMap.put("data", PageList.success(size,page,(int)count,taskDTOList));
            return resultMap;
        }
        Integer startPosition = (page - 1) * size;
        // 历史任务Service
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .taskAssignee(currentUserId)
                .finished()
                .orderByHistoricTaskInstanceEndTime().desc()
                .listPage(startPosition,size);
        ScTaskDTO taskDTO;
        ProcessInstance processInstance;
        String processDefinitionKey;
        HistoricProcessInstance historicProcessInstance;
        for(HistoricTaskInstance hisTask:list){
            taskDTO = new ScTaskDTO();
            String processInstanceId = hisTask.getProcessInstanceId();
            // 根据流程实例id获取流程实例
            processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            //流程已经结束
            if(processInstance == null){
                historicProcessInstance = historyService
                        .createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .singleResult();
                processDefinitionKey = historicProcessInstance.getProcessDefinitionKey();
                taskDTO.setProcessDefinitionKey(processDefinitionKey);
                taskDTO.setFinished(1);
            }else {
                processDefinitionKey = processInstance.getProcessDefinitionKey();
                taskDTO.setProcessDefinitionKey(processDefinitionKey);
                taskDTO.setFinished(0);
            }
            setProcessInstanceVariable(processInstanceId, taskDTO, hisTask);
            setTaskVariable(hisTask.getId(), taskDTO);

            taskDTO.setProcessInstanceId(processInstanceId);
            taskDTO.setTaskDefinitionKey(hisTask.getTaskDefinitionKey());
            taskDTO.setTaskId(hisTask.getId());
            taskDTO.setTaskName(hisTask.getName());
            taskDTO.setTaskAssignee(hisTask.getAssignee());
            taskDTO.setTaskStartTime(hisTask.getCreateTime());
            taskDTO.setTaskEndTime(hisTask.getEndTime());
            taskDTOList.add(taskDTO);
        }
        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "查询成功！");
        resultMap.put("data", PageList.success(size,page,(int)count,taskDTOList));
        return resultMap;
    }

    private void setProcessInstanceVariable(String processInstanceId, ScTaskDTO taskDTO, HistoricTaskInstance hisTask) {
        List<HistoricVariableInstance> historyVariableList = historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId).list();
        String creator;
        Integer processType;
        String processName;
        Long offeringId;
        for (HistoricVariableInstance variable: historyVariableList) {
            if("creator".equals(variable.getVariableName())){
                creator = (String)variable.getValue();
                taskDTO.setCreator(creator);
            }
            if("processType".equals(variable.getVariableName())){
                processType = (Integer)variable.getValue();
                taskDTO.setProcessType(processType);
                taskDTO.setProcessTypeName(ProcessTypeEnum.getTypeDesc(processType));
            }
            if("processName".equals(variable.getVariableName())){
                processName = (String)variable.getValue();
                taskDTO.setTaskTitle("（"+hisTask.getName()+"）"+processName);
            }
            if("offeringId".equals(variable.getVariableName())){
                offeringId = (Long)variable.getValue();
                taskDTO.setOfferingId(offeringId);
            }
        }
    }

    @Override
    public Map<String, Object> getApprovalRecord(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String, Object> resultMap = new HashMap <>();
        if(StringUtils.isEmpty(scProcessParamsDTO.getProcessInstanceId())){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误：缺少流程实例ID");
            resultMap.put("data", null);
        }
        List<ScTaskDTO> taskDTOS = new ArrayList <>();

        findHistoryActivities(scProcessParamsDTO.getProcessInstanceId(), taskDTOS);
        findCurrentTask(scProcessParamsDTO.getProcessInstanceId(), taskDTOS);

        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "查询成功！");
        resultMap.put("data", taskDTOS);
        return resultMap;
    }

    /**查询历史任务*/
    private void findHistoryActivities(String processInstanceId, List<ScTaskDTO> taskDTOS){
        List<HistoricActivityInstance> list = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        Map<String, Object> processInstanceVariables = getProcessInstanceVariable(processInstanceId);
        for (HistoricActivityInstance item : list) {
            ScTaskDTO taskDTO = new ScTaskDTO();
            String activityType = item.getActivityType();
            if(!"userTask".equals(activityType) && !"serviceTask".equals(activityType)){
                continue;
            }
            taskDTO.setTaskId(item.getTaskId());
            taskDTO.setTaskName(item.getActivityName());
            taskDTO.setTaskAssignee(item.getAssignee());
            taskDTO.setTaskStartTime(item.getStartTime());
            taskDTO.setTaskEndTime(item.getEndTime());
            taskDTO.setProcessType((Integer) processInstanceVariables.get("processType"));
            taskDTO.setProcessDefinitionKey(ProcessTypeEnum.getType(taskDTO.getProcessType()));
            taskDTO.setProcessTypeName(ProcessTypeEnum.getTypeDesc(taskDTO.getProcessType()));
            taskDTO.setCreator((String) processInstanceVariables.get("creator"));
            taskDTO.setTaskTitle("（"+ item.getActivityName() +"）"+processInstanceVariables.get("processName"));
            taskDTO.setOfferingId((Long) processInstanceVariables.get("offeringId"));
            setTaskVariable(item.getTaskId(), taskDTO);
            taskDTO.setFinished(1);
            taskDTOS.add(taskDTO);
        }
    }
    private Map<String, Object> getProcessInstanceVariable(String processInstanceId) {
        Map<String, Object> resultMap = new HashMap <>();
        List<HistoricVariableInstance> historyVariableList = historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId).list();
        String creator;
        Integer processType;
        String processName;
        Long offeringId;
        for (HistoricVariableInstance variable: historyVariableList) {
            if("creator".equals(variable.getVariableName())){
                creator = (String)variable.getValue();
                resultMap.put("creator", creator);
            }
            if("processType".equals(variable.getVariableName())){
                processType = (Integer)variable.getValue();
                resultMap.put("processType", processType);
            }
            if("processName".equals(variable.getVariableName())){
                processName = (String)variable.getValue();
                resultMap.put("processName", processName);
            }
            if("offeringId".equals(variable.getVariableName())){
                offeringId = (Long)variable.getValue();
                resultMap.put("offeringId", offeringId);
            }
        }
        return resultMap;
    }
    private void setTaskVariable (String taskId, ScTaskDTO taskDTO) {
        if(StringUtils.isEmpty(taskId)){
            return;
        }
        List<HistoricVariableInstance> variablesList = historyService.createHistoricVariableInstanceQuery()
                .taskId(taskId)
                .list();
        for (HistoricVariableInstance variable : variablesList) {
            if("prevUser".equals(variable.getVariableName())){
                taskDTO.setPreviousUser((String)variable.getValue());
            }
            if("comment".equals(variable.getVariableName())){
                taskDTO.setTaskComment((String)variable.getValue());
            }
            if("operationType".equals(variable.getVariableName())){
                taskDTO.setOperationType((Integer)variable.getValue());
                taskDTO.setOperationTypeName(OperationTypeEnum.getTypeDesc((Integer)variable.getValue()));
            }
        }
    }
    private void findCurrentTask (String processInstanceId, List<ScTaskDTO> taskDTOS) {
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if(task == null){
            return;
        }
        Map<String, Object> variables = taskService.getVariables(task.getId());
        ScTaskDTO taskDTO = new ScTaskDTO();
        taskDTO.setTaskTitle("（"+task.getName()+"）"+(String)variables.get("processName"));
        taskDTO.setCreator((String)variables.get("creator"));
        taskDTO.setPreviousUser((String)variables.get("prevUser"));
        taskDTO.setProcessType((Integer)variables.get("processType"));
        taskDTO.setProcessTypeName(ProcessTypeEnum.getTypeDesc((Integer)variables.get("processType")));
        taskDTO.setProcessInstanceId(processInstanceId);
        taskDTO.setTaskId(task.getId());
        taskDTO.setTaskName(task.getName());
        taskDTO.setTaskAssignee(task.getAssignee());
        taskDTO.setTaskStartTime(task.getCreateTime());
        taskDTO.setTaskDefinitionKey(task.getTaskDefinitionKey());
        taskDTO.setFinished(0);
        taskDTOS.add(taskDTO);
    }

    @Override
    public Map<String, Object> assignTask(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String, Object> resultMap = new HashMap <>();
        if(StringUtils.isEmpty(scProcessParamsDTO.getTaskId()) ||
                StringUtils.isEmpty(scProcessParamsDTO.getTargetUserId())){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误");
            return resultMap;
        }
        taskService.setAssignee(scProcessParamsDTO.getTaskId(), scProcessParamsDTO.getTargetUserId());
        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "操作成功");
        return resultMap;
    }

    @Override
    public Map<String, Object> getNextNodes(ScProcessParamsDTO scProcessParamsDTO) {
        Map<String, Object> resultMap = new HashMap <>();
        if(scProcessParamsDTO.getCurOperationUserRole().size() == 0
                || scProcessParamsDTO.getProcessType() == null){
            resultMap.put("code", InterfaceCodeEnum.FAIL);
            resultMap.put("message", "参数错误");
            return resultMap;
        }
        Set<String> targetRoles = new HashSet <>();
        String definitionKey = ProcessTypeEnum.getType(scProcessParamsDTO.getProcessType());
        BpmnModel bpmnModel = getBpmnModelByDefinitionKey(definitionKey);
        Process process = bpmnModel.getMainProcess();
        List<FlowElement> flowElements = (List <FlowElement>) process.getFlowElements();
        for (FlowElement fe: flowElements) {
            if(fe instanceof ExclusiveGateway){
                ExclusiveGateway exclusiveGateway = (ExclusiveGateway)fe;
                List<SequenceFlow> sequenceFlows = exclusiveGateway.getOutgoingFlows();
                for (SequenceFlow sflow: sequenceFlows) {
                    String targetRef = sflow.getTargetRef();
                    if("updateInfo".equals(targetRef) || "updateOfferStatus".equals(targetRef) || "resetOfferStatus".equals(targetRef)){
                        continue;
                    }
                    String conditionExpression = sflow.getConditionExpression();
                    Boolean contained = judgeIsContainRole(conditionExpression, scProcessParamsDTO.getCurOperationUserRole());
                    if(contained){
                        targetRoles.add(targetRef);
                    }
                }
            }
        }
        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "查询成功");
        resultMap.put("data", targetRoles);
        return resultMap;
    }

    private Boolean judgeIsContainRole(String conditionExpression, List<String> curOperationUserRole) {
        for (String role: curOperationUserRole) {
            if(conditionExpression.contains(role)){
                return true;
            }
        }
        return false;
    }
    private BpmnModel getBpmnModelByDefinitionKey(String definitionKey){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(definitionKey).latestVersion().singleResult();
        return repositoryService.getBpmnModel(processDefinition.getId());
    }
    private BpmnModel getBpmnModelByTaskId(String taskId){
        TaskService taskService = activitiRule.getTaskService();
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            System.out.println("未查询到task！");
        }
        return repositoryService.getBpmnModel(Objects.requireNonNull(task).getProcessDefinitionId());
    }


    @Override
    public Map<String,Object> deleteProcessDefinition(String deploymentId){

        Map<String,Object> resultMap = new HashMap <>();
       // 级联删除 不管流程是否启动，都能可以删除
        processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
        resultMap.put("code", InterfaceCodeEnum.SUCCESS);
        resultMap.put("message", "操作成功！");
        return resultMap;
    }

    /**
     * 获取流程变量
     */
    @Override
    public Map<String,Object> getVariables(String taskId) {
        return taskService.getVariables(taskId);
    }


    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

}
