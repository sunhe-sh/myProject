package com.sunspring.wechat.dto;


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
@XmlRootElement(name = "ScanCodeInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScanCodeItem {
    /**
     * 扫描类型，一般是qrcode
     */
    private String ScanType;
    /**
     * 扫描结果，即二维码对应的字符串信息
     */
    private String ScanResult;



}
