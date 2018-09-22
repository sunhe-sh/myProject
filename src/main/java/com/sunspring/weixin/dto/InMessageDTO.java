package com.sunspring.weixin.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;

/**
 * @author sunhe
 */
@Setter
@Getter
@ToString
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class InMessageDTO {
    /**
     * 开发者微信号
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
     * 消息类型 参见枚举类：MessageTypeEnum
     */
    private String MsgType;
    /**
     * 文本消息内容
     */
    private String Content;
    /**
     * 消息id，64位整型
     */
    private Long MsgId;
    /**
     * 图片链接（由系统生成）
     */
    private String PicUrl;
    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String MediaId;
    /**
     * 事件类型 参见枚举类：EventTypeEnum
     */
    private String Event;
    /**
     * 事件KEY值
     * 一、扫描带参数二维码事件
     *  1.用户未关注时，进行关注后的事件：qrscene_为前缀，后面为二维码的参数值
     *  2.用户已关注时的事件：是一个32位无符号整数，即创建二维码时的二维码scene_id
     * 二、自定义菜单事件
     *  1.点击菜单拉取消息时的事件：与自定义菜单接口中KEY值对应
     *  1.点击菜单跳转链接时的事件：设置的跳转URL
     */
    private String EventKey;
    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String Ticket;
    /**
     * 	地理位置纬度
     */
    private Double Latitude;
    /**
     * 	地理位置经度
     */
    private Double Longitude;
    /**
     * 	地理位置精度
     */
    private Double Precision;

    /**
     * 扫描信息
     */
    private ScanCodeItem ScanCodeInfo;

    /**
     * 地理位置纬度
     */
    private Double Location_X;
    /**
     * 地理位置经度
     */
    private Double Location_Y;
    /**
     * 地图缩放大小
     */
    private Double Scale;
    /**
     * 地理位置信息
     */
    private String Label;

}
