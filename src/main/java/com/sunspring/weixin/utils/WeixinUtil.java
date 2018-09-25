package com.sunspring.weixin.utils;

import com.sunspring.weixin.dto.TemplateMessageDTO;
import com.sunspring.weixin.dto.TemplateMessageDataItem;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunhe
 */
@Component
public class WeixinUtil {

    public static final String TOKEN = "wx_service_sh";
    private static final String APP_ID = "wx132b689efeb0f7d7";
    private static final String APP_SECRET = "c346c42697194237ba92cf203658b58a";
    /**
     * 创建自定义菜单接口
     */
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    /**
     * 删除自定义菜单接口
     */
    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    /**
     * 获取accessToke接口
     */
    private static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 发送模板消息接口
     */
    private static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    private static String accessToken;
    private static Long expireTime;
    /**
     * 获取Accesstoken凭据
     * @return accessToken
     */
    private static String getAccessToken() {
        //accessToken为空 或 已经失效时进行重新获取
        if(StringUtils.isEmpty(WeixinUtil.accessToken) || System.currentTimeMillis() > WeixinUtil.expireTime){
            JSONObject jsonObject = HttpUtil.get(GET_ACCESSTOKEN_URL.replace("APPID",APP_ID).replace("APPSECRET",APP_SECRET));
            //凭据
            WeixinUtil.accessToken = jsonObject.getString("access_token");
            //有效期
            Long expires_in = jsonObject.getLong("expires_in");
            WeixinUtil.expireTime = System.currentTimeMillis() + ((expires_in - 60) * 1000);
        }
        return WeixinUtil.accessToken;
    }

    private static String jsonMenu = "{\n" +
            "     \"button\":[\n" +
            "     {    \n" +
            "          \"type\":\"scancode_push\",\n" +
            "          \"name\":\"扫码取号\",\n" +
            "          \"key\":\"V1001_SCANCODE_PUSH\"\n" +
            "      },\n" +
            "     {    \n" +
            "          \"type\":\"click\",\n" +
            "          \"name\":\"预约取号\",\n" +
            "          \"key\":\"V1001_CURRENT_TIME\"\n" +
            "      },\n" +
            "     {    \n" +
            "          \"type\":\"location_select\",\n" +
            "          \"name\":\"我的\",\n" +
            "          \"key\":\"V1001_LOCATION_SELECT\"\n" +
            "      }" +
            " }";

    private static String jsonTemplateMsg = "{\n" +
            "           \"touser\":\"ohm_z1EjFrY2vTmLbRSK0haIV10w\",\n" +
            "           \"template_id\":\"8hnejHal2RdZruS5IF3LUgbQbimSBkgugKrw8Vo0h_k\",\n" +
            "           \"url\":\"https://www.baidu.com\",\n" +
            "           \"data\":{\n" +
            "                   \"top\": {\n" +
            "                       \"value\":\"尊敬的XXX，您好！\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"deptName\":{\n" +
            "                       \"value\":\"七里庄营业厅\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"currentNum\": {\n" +
            "                       \"value\":\"GH1024\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"serviceType\": {\n" +
            "                       \"value\":\"宽带报装\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"aheadNum\": {\n" +
            "                       \"value\":\"20人\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"ewt\": {\n" +
            "                       \"value\":\"10分钟\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"bottom\":{\n" +
            "                       \"value\":\"我们将尽快为您服务，请留意微信平台排号变化通知，中国联通温馨提醒！\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   }\n" +
            "           }\n" +
            "       }";

    /**
     * 创建自定义菜单
     * @param jsonParam json数据
     */
    public static void createMenu(String jsonParam) {
        JSONObject resultJson =  HttpUtil.post(CREATE_MENU_URL.replace("ACCESS_TOKEN",getAccessToken()),jsonParam);
        System.out.println("createMenu>>>>>>>>>>>>>>>>>>>>"+resultJson);
    }

    /**
     * 删除自定义菜单
     */
    public static void deleteMenu() {
        JSONObject resultJson =  HttpUtil.get(DELETE_MENU_URL.replace("ACCESS_TOKEN",getAccessToken()));
        System.out.println("deleteMenu>>>>>>>>>>>>>>>>>>>>"+resultJson);
    }

    /**
     * 发送模板消息
     */
    public static void sendTemplateMessage(TemplateMessageDTO messageDTO) {
        JSONObject resultJson =  HttpUtil.post(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN",getAccessToken()),messageDTO.toString());
        System.out.println("sendTemplateMessage>>>>>>>>>>>>>>>>>" + resultJson);
    }

    public static void main(String[] args) {

//        sendTemplateMsg_TEST();

        createMenu(jsonMenu);
//        System.out.println(WeixinUtil.accessToken);
//        System.out.println(getAccessToken());
//        System.out.println(getAccessToken());
//        System.out.println(getAccessToken());
    }
    private static void sendTemplateMsg_TEST(){
        Map<String, TemplateMessageDataItem> dataMap = new HashMap<>();
        TemplateMessageDataItem top = new TemplateMessageDataItem();
        top.setValue("尊敬的XXX，您好！");
        top.setColor("#173177");
        TemplateMessageDataItem bottom = new TemplateMessageDataItem();
        bottom.setValue("我们将尽快为您服务，请留意微信平台排号变化通知，中国联通温馨提醒！");
        bottom.setColor("#173177");
        dataMap.put("top", top);
        dataMap.put("bottom", bottom);
        TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO();
        templateMessageDTO.setTouser("ohm_z1EjFrY2vTmLbRSK0haIV10w");
        templateMessageDTO.setTemplate_id("8hnejHal2RdZruS5IF3LUgbQbimSBkgugKrw8Vo0h_k");
        templateMessageDTO.setUrl("https://www.baidu.com");
        templateMessageDTO.setData(dataMap);

        sendTemplateMessage(templateMessageDTO);
    }
}
