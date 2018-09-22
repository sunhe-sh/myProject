package com.sunspring.weixin.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sunhe
 */
@Setter
@Getter
@ToString
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutMessageDTO {

    /**
     * 用户的openId
     */
    private String ToUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    private String FromUserName;
    /**
     * 消息创建时间 （整型）
     */
    private Long CreateTime;
    /**
     * MessageTypeEnum
     */
    private String MsgType;
    /**
     * 文本消息内容
     */
    private String Content;
    /**
     *
     * 通过素材管理中的接口上传多媒体文件，得到的id
     */
    @XmlElementWrapper(name = "Image")
    private String[] MediaId;
    /**
     * 视频消息的标题 || 音乐标题
     */
    private String Title;
    /**
     * 视频消息的描述 || 音乐描述
     */
    private String Description;
    /**
     * 音乐链接
     */
    private String MusicURL;
    /**
     * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
     */
    private String HQMusicUrl;
    /**
     * 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
     */
    private String ThumbMediaId;
    /**
     * 图文消息个数，限制为8条以内
     */
    private Integer ArticleCount;
    /**
     * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
     */
    @XmlElementWrapper(name = "Articles")
    private ArticleItem[] item;

}
