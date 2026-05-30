package com.cmbccd.ulms.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 
* <p>Title: CorsConfig.java</p>  
* <p>Description: 跨域配置</p>  
* @author WeiGenSheng
* @date 2020年5月5日
* @version 1.0
 */
@Configuration
public class CorsConfig {
    Logger log = LoggerFactory.getLogger(CorsConfig.class);
    /**
     * 跨域配置
     * @return CorsConfiguration
     */
    @Bean
    @NonNull
    public CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 预检请求的有效期，单位为秒。
        corsConfiguration.setMaxAge(3600L);
        // 是否支持安全证书(必需参数)
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:*",
            "http://127.0.0.1:*",
            "http://10.47.81.*",
            "http://192.168.*",
            "http://101.35.25.22",
            "http://wei6130.site"
        ));
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        return corsConfiguration;
    }

    /**
     * 设置静态资源访问
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
