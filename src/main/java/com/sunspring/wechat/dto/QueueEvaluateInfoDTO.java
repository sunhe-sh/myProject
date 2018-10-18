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
public class QueueEvaluateInfoDTO {
    /**
     * 主键ID
     */
    private String evaId;
    /**
     * 评价项名称
     */
    private String evaName;
    /**
     * 顺序
     */
    private String evaSeq;
    /**
     * 评价项分值
     */
    private String evaScore;
    /**
     * 状态
     */
    private String evaStatus;
    /**
     * 门店ID
     */
    private String deptId;
    /**
     * 备注
     */
    private String evaMemo;
}
