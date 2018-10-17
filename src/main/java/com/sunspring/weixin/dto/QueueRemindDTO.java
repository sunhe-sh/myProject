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
public class QueueRemindDTO {
    /**
     * 顾客预约取号表 主键
     * queue_client_subscribe
     */
    private String subId;
    /**
     * 微信用户openID
     */
    private String openId;
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 营业厅id
     */
    private Integer deptId;
    /**
     * 营业厅全称
     */
    private String deptName;
    /**
     * 业务id
     */
    private Integer businessId;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 当前排号
     */
    private String currentNum;
    /**
     * 预约时间
     */
    private String subTime;
    /**
     * 前方等待人数
     */
    private String aheadNum;
    /**
     * 排队总人数
     */
    private String waitTotalNum;
    /**
     * 预计等待时间
     */
    private String waitTime;
}
