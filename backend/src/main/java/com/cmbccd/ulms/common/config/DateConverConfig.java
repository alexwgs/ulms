package com.cmbccd.ulms.common.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* <p>Title: DateConverConfig.java</p>  
* <p>Description: 日期转换配置</p>  
* @author WeiGenSheng
* @date 2020年5月5日  
* @version 1.0
 */
@Configuration
public class DateConverConfig {
    private static final Logger log = LoggerFactory.getLogger(DateConverConfig.class);
    private static final ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    @Bean
    public Converter<String, Date> stringDateConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = DATE_TIME_FORMAT.get();
                Date date = null;
                try {
                    date = sdf.parse((String) source);
                } catch (Exception e) {
                    SimpleDateFormat sdfday = DATE_FORMAT.get();
                    try {
                        date = sdfday.parse((String) source);
                    } catch (ParseException e1) {
                        log.warn("日期格式解析失败: {}", source);
                    }
                }
                return date;
            }
        };
    }

}
