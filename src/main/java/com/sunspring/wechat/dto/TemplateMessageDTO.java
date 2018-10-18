package com.sunspring.wechat.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 模板消息
 * @author sunhe
 */
@Getter
@Setter
public class TemplateMessageDTO {

    /**
     * 接收者openid
     */
    private String touser;
    /**
     * 模板ID
     */
    private String template_id;
    /**
     * 模板跳转链接
     */
    private String url;
    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     * 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
     */
    private String appid;
    /**
     * 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），暂不支持小游戏
     */
    private String pagepath;
    /**
     * 模板数据
     */
    private Map<String, TemplateMessageDataItem> data;

    public TemplateMessageDTO() {
    }

    public TemplateMessageDTO(String touser, String template_id, String url, String appid, String pagepath, Map <String, TemplateMessageDataItem> data) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
        this.appid = appid;
        this.pagepath = pagepath;
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"touser\":\"" + touser + "\",\n" +
                "    \"template_id\":\"" + template_id + "\",\n" +
                     (StringUtils.isEmpty(url)? "" : "\"url\":\"" + url + "\",\n")+
                     generateMiniprogram() +
                "    \"data\":" +generateData() +
                "}";
    }

    /**
     * 处理模板数据
     * @return data
     */
    private String generateData(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, TemplateMessageDataItem> item : data.entrySet()) {
            sb.append("\"").append(item.getKey()).append("\":");
            sb.append("{");
            sb.append("\"value\":\"").append(item.getValue().getValue()).append("\",");
            sb.append("\"color\":\"").append(item.getValue().getColor()).append("\"");
            sb.append("},");
        }
        return sb.substring(0, sb.length()-1) + "}";
    }
    /**
     * 处理miniprogram
     * @return data
     */
    private String generateMiniprogram(){
        String miniprogram = "";
        if(!StringUtils.isEmpty(appid)){
            miniprogram =" \"miniprogram\":{\n" +
                        "   \"appid\":\"" + appid + "\",\n" +
                          (StringUtils.isEmpty(pagepath)? "" : "\"pagepath\":\"" + pagepath + "\"\n")+
                        " },  ";
        }
        return miniprogram;
    }

}
