package com.sunspring.activiti.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 工作流 -- 流程分支判断
 * @author sunhe
 */
@Service(value = "scProcessExpressionService")
public class ScProcessExpressionFunction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 判断角色
     * @param sourceRoles
     * @param targetRole
     * @return
     */
    public boolean contain(List<String> sourceRoles, String targetRole){

        if(sourceRoles.size() == 0 || StringUtils.isEmpty(targetRole)){
            return false;
        }
        if(sourceRoles.contains(targetRole)){
            return true;
        }
        return false;
    }
}
