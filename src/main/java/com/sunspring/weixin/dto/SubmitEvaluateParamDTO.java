package com.sunspring.weixin.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author sunhe
 */
@Getter
@Setter
@ToString
public class SubmitEvaluateParamDTO {
    /**
     * 微信openid
     */
    private String openId;
    /**
     * 门店ID
     */
    private Integer deptId;
    /**
     * queue_user_business表 主键
     */
    private Integer operId;
    /**
     * 建议
     */
    private String advice;
    /**
     * 评价列表
     */
    private List<EvaluateItem> evaList;

}
