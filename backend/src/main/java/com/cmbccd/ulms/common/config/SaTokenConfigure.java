package com.cmbccd.ulms.common.config;


import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
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
        // 审计加固：全局登录检查 —— 除公开端点外，所有请求必须登录（阻断匿名调用业务接口）。
        // 方法级 @SaIgnore（如文件下载）仍可放行；WebSocket 握手 /ws/** 由 WebSocket 容器
        // 独立处理并自行校验 token，不经过本 MVC 拦截器。
        log.info("注册 Sa-Token 拦截器：注解鉴权 + 全局登录检查");
        registry.addInterceptor(new SaInterceptor(handle -> {
            SaRouter.match("/**")
                    .notMatch(
                            "/static/**",       // 前端构建产物 (JS/CSS/字体/图片)
                            "/index.html",      // SPA 入口
                            "/favicon.ico",     // 浏览器图标
                            "/error",           // 错误处理端点
                            "/api/login",       // 登录接口
                            "/api/logout",      // 登出：未登录时也应可调用，避免前端 token 残留
                            "/api/unauth",      // 未登录提示端点
                            "/api/403",         // 无权限提示端点
                            "/api/swagger-ui/**", // API 文档（prod 已通过 springdoc.enabled=false 禁用）
                            "/api/v3/api-docs/**"
                    )
                    .check(r -> StpUtil.checkLogin());
        }))
                .addPathPatterns("/**")
                // 拦截器级排除（完全跳过拦截器，与 SaRouter.notMatch 双保险）
                .excludePathPatterns(
                        "/static/**",
                        "/index.html",
                        "/favicon.ico",
                        "/error",
                        "/api/login",
                        "/api/logout",
                        "/api/unauth",
                        "/api/403"
                );
    }
}
