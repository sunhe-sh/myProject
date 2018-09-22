package com.sunspring.weixin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunhe
 */
@Getter
@Setter
public class TemplateMessageDataItem {

    /**
     *
     */
    private String value;
    /**
     * 模板内容字体颜色，不填默认为黑色
     */
    private String color;

}
