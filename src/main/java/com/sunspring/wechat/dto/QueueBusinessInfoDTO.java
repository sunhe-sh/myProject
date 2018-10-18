package com.sunspring.wechat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author sunhe
 */
@Getter
@Setter
@ToString
public class QueueBusinessInfoDTO {
    /**
     * 业务名称
     */
    private String busName;
    /**
     * 业务编号
     */
    private String busNum;
    /**
     * 业务优先级
     */
    private Integer busGrade;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 门店ID
     */
    private Integer deptId;

}
