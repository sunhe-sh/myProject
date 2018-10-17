package com.sunspring.weixin.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sunhe
 */
@Setter
@Getter
@ToString
public class LocationReportDTO {

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

}
