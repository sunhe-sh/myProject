package com.sunspring.weixin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EvaluateItem {

    /**
     * queue_evaluate_info 表 主键
     */
    private Integer evaId;
    /**
     * 评价项名称
     */
    private String evaName;
    /**
     * 分值（待计算）
     */
    private Double inputScore;
}
