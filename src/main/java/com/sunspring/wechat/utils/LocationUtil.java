package com.sunspring.wechat.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 坐标计算工具类
 * @author sunhe
 */
public class LocationUtil {
    /**
     * 地球半径 单位：千米
     */
    private static double EARTH_RADIUS = 6378.137;

    /**
     * 获取限定半径的坐标范围
     *  * 此方法返回正方形区域，若需要圆形区域，需要在业务结果集中计算两坐标距离进行过滤
     *
     * @param latitude 圆心坐标纬度
     * @param longitude 圆心坐标经度
     * @param distance 半径
     * @return 坐标范围
     */
    public static Map<String, Object> findNeighPosition(Double latitude, Double longitude, Double distance){
        Map<String, Object> resultMap = new HashMap <>(4);
        double dlng =  2*Math.asin(Math.sin(distance/(2*EARTH_RADIUS))/Math.cos(latitude*Math.PI/180));
        //角度转为弧度
        dlng = dlng*180/Math.PI;
        double dlat = distance/EARTH_RADIUS;
        dlat = dlat*180/Math.PI;
        double minlat = latitude-dlat;
        double maxlat = latitude+dlat;
        double minlng = longitude -dlng;
        double maxlng = longitude + dlng;
        resultMap.put("minLatitude", minlat);
        resultMap.put("maxLatitude", maxlat);
        resultMap.put("minLongitude", minlng);
        resultMap.put("maxLongitude", maxlng);
        return resultMap;
    }

    /**
     * 计算两坐标直线距离(单位：米)
     * @param lat1 坐标1纬度
     * @param lng1 坐标1经度
     * @param lat2 坐标2纬度
     * @param lng2 坐标2经度
     * @return double 距离(单位：米)
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)	* Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
