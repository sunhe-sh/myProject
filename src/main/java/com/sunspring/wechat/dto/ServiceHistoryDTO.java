package com.sunspring.wechat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sunhe
 */
@Getter
@Setter
@ToString
public class ServiceHistoryDTO {
    /**
     * 评价ID
     */
    private String evaInsId;
    /**
     * 预约ID
     */
    private String subId;
    /**
     * 营业员办理ID
     */
    private String operId;
    /**
     * 营业厅ID
     */
    private String deptId;
    /**
     * 营业厅名称
     */
    private String deptName;
    /**
     * 业务类型名称
     */
    private String businessName;
    /**
     * 业务类型代码
     */
    private Integer businessNum;
    /**
     * 完成时间
     */
    private String finishTime;
    /**
     * 办理状态值
     */
    private String operationStatus;
    /**
     * 办理状态
     */
    private String operationStatusDesc;
    /**
     * 评价状态值
     */
    private String evaluateStatus;
    /**
     * 评价状态
     */
    private String evaluateStatusDesc;
}
