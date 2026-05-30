package com.cmbccd.ulms.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
/**
* <p>Title: ScheduledConfig.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年5月5日  
* @version 1.0
 */
@Configuration
public class ScheduledConfig {
    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }
}
