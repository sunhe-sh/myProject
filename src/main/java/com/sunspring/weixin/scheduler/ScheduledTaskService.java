package com.sunspring.weixin.scheduler;

import com.sunspring.weixin.utils.WeChatUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author sunhe
 */
@Service
public class ScheduledTaskService {

    /**
     * 每天 0点 清空位置信息Map
     */
    @Scheduled(cron = "0 0 0 ? * *")
    public void clearLocationMap(){
        System.out.println("clearLocationMap>>> " + WeChatUtil.locationMap.size() + " cleared");
        WeChatUtil.locationMap.clear();
    }
}
