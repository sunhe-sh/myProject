package com.sunspring.weixin.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sunhe
 */
@Setter
@Getter
@ToString
@XmlRootElement(name = "Articles")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleItem {

    /**
     * 图文消息标题
     */
    private String Title;
    /**
     * 图文消息描述
     */
    private String Description;
    /**
     * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
     */
    private String PicUrl;
    /**
     * 点击图文消息跳转链接
     */
    private String Url;



}
