package com.sunspring.weixin.utils;

import com.sunspring.weixin.dto.CreateQrcodeParamDTO;
import com.sunspring.weixin.dto.TemplateMessageDTO;
import com.sunspring.weixin.dto.TemplateMessageDataItem;
import com.sunspring.weixin.enums.QrcodeActionNameEnum;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunhe
 */
@Component
public class WeChatUtil {

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
    /**
     * 生成带参数的二维码接口
     */
    private static final String QRCODE_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
    /**
     * 通过ticket换取二维码接口
     */
    private static final String QRCODE_GET_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

    private static String accessToken;
    private static Long expireTime;
    /**
     * 获取Accesstoken凭据
     * @return accessToken
     */
    private static String getAccessToken() {
        //accessToken为空 或 已经失效时进行重新获取
        if(StringUtils.isEmpty(WeChatUtil.accessToken) || System.currentTimeMillis() > WeChatUtil.expireTime){
            JSONObject jsonObject = HttpUtil.get(GET_ACCESSTOKEN_URL.replace("APPID",APP_ID).replace("APPSECRET",APP_SECRET));
            //凭据
            WeChatUtil.accessToken = jsonObject.getString("access_token");
            //有效期
            Long expires_in = jsonObject.getLong("expires_in");
            WeChatUtil.expireTime = System.currentTimeMillis() + ((expires_in - 60) * 1000);
        }
        return WeChatUtil.accessToken;
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


    private static String qrcodeParam = "{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"SCAN_QRCODE_NUM\",\"scene_id\":}}}";

    /**
     * 创建自定义菜单
     * @param jsonParam json数据
     */
    public static void createMenu(String jsonParam) {
        JSONObject resultJson =  HttpUtil.post(CREATE_MENU_URL.replace("ACCESS_TOKEN",getAccessToken()),jsonParam);
        System.out.println(">>>createMenu>>>>>>>>>>>>>>>>>>>>"+resultJson);
    }

    /**
     * 删除自定义菜单
     */
    public static void deleteMenu() {
        JSONObject resultJson =  HttpUtil.get(DELETE_MENU_URL.replace("ACCESS_TOKEN",getAccessToken()));
        System.out.println(">>>deleteMenu>>>>>>>>>>>>>>>>>>>>"+resultJson);
    }

    /**
     * 发送模板消息
     */
    public static void sendTemplateMessage(TemplateMessageDTO messageDTO) {
        JSONObject resultJson =  HttpUtil.post(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN",getAccessToken()),messageDTO.toString());
        System.out.println(">>>sendTemplateMessage>>>>>>>>>>>>>>>>>" + resultJson);
    }

    /**
     * 创建带参数的公众号二维码图片
     * @param paramDTO
     * @return  ticket 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
     *          expire_seconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）
     *          url 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    public static JSONObject createQrcode(CreateQrcodeParamDTO paramDTO) {
        JSONObject resultJson =  HttpUtil.post(QRCODE_CREATE_URL.replace("ACCESS_TOKEN",getAccessToken()),paramDTO.toString());
        System.out.println(">>>createQrcode>>>>>>>>>>>>>>>>>" + resultJson);
        return resultJson;
    }

    /**
     * 生成二维码图片，并返回地址
     * @param paramDTO
     */
    public static String getQrcodeUrl(CreateQrcodeParamDTO paramDTO) {
        JSONObject resultJson =  createQrcode(paramDTO);
        if(resultJson != null && !resultJson.containsKey("errcode")){
            return QRCODE_GET_URL.replace("TICKET",  (String)resultJson.get("ticket"));
        }
        return resultJson.toString();
    }

    public static void main(String[] args) {

        createQrcode_TEST();

//        createMenu(jsonMenu);

//        System.out.println(WeixinUtil.accessToken);
//        System.out.println(getAccessToken());
//        System.out.println(getAccessToken());
//        System.out.println(getAccessToken());

//        sendTemplateMsg_TEST();
    }

    private static void createQrcode_TEST() {
        CreateQrcodeParamDTO paramDTO = new CreateQrcodeParamDTO();
        paramDTO.setExpire_seconds(604800L);
        paramDTO.setAction_name(QrcodeActionNameEnum.QR_STR_SCENE.getActionName());
        paramDTO.setScene_str("场景id");
//        createQrcode(paramDTO);

        getQrcodeUrl(paramDTO);
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
