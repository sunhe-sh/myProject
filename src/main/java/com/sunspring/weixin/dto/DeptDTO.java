package com.sunspring.weixin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sunhe
 */
@Getter
@Setter
@ToString
public class DeptDTO {

    /**
     * 营业厅id
     */
    private Integer id;
    /**
     * 营业厅地址
     */
    private String deptAddress;

    /**
     * 营业厅纬度
     */
    private String deptLat;
    /**
     * 营业厅经度
     */
    private String deptLng;
    /**
     * 营业厅类型：1门店，2自营厅，3部门
     */
    private Integer deptType;
    /**
     * 全称
     */
    private String fullname;
    /**
     * 简称
     */
    private String simplename;
    /**
     * 与目标地点距离
     */
    private Double distance;

    /**
     * 排队人数
     */
    private Integer waitingNum;
    /**
     * 联系电话
     */
    private String conTel;
}
