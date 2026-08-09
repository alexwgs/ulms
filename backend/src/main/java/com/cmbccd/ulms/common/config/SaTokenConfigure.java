package com.cmbccd.ulms.common.config;


import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer  {
    private static final Logger log = LoggerFactory.getLogger(SaTokenConfigure.class);

    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        log.info("注册 Sa-Token 拦截器，打开注解式鉴权功能");
        registry.addInterceptor(new SaInterceptor())
                .addPathPatterns("/**")
                // 排除静态资源和公开端点（避免拦截器对 JS/CSS/图片 返回 JSON 导致 MIME 错误）
                .excludePathPatterns(
                        "/static/**",       // 前端构建产物 (JS/CSS/字体/图片)
                        "/index.html",      // SPA 入口
                        "/favicon.ico",     // 浏览器图标
                        "/api/upload/**",   // 上传文件（头像等，接口统一加 /api 前缀）
                        "/error",           // 错误处理端点
                        "/api/login",       // 登录接口
                        "/api/getPw"        // 获取加密密钥
                );
    }
}
