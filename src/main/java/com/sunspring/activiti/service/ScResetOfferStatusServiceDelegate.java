package com.sunspring.activiti.service;

import com.sunspring.activiti.enums.ProcessTypeEnum;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 工作流 -- 放弃申请
 * 流程结束后，商品状态修改
 * @author sunhe
 */
@Service(value = "scResetOfferStatusServiceDelegate")
public class ScResetOfferStatusServiceDelegate implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Long offeringId = (Long) delegateExecution.getVariable("offeringId");
        Integer processType = (Integer) delegateExecution.getVariable("processType");
        Integer offeringSource = (Integer) delegateExecution.getVariable("offeringSource");
        Integer deptId = (Integer) delegateExecution.getVariable("deptId");

        //上架流程
        if(ProcessTypeEnum.SHELF_PROCESS.getValue() == processType){
            System.out.println("放弃申请：处理上架流程");
        //下架流程
        } else if(ProcessTypeEnum.UNSHELF_PROCESS.getValue() == processType){
            System.out.println("放弃申请：处理下架流程");

        }
    }
}
