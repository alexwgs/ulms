package com.cmbccd.ulms.common.config;


import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer  {
    private static final Logger log = LoggerFactory.getLogger(SaTokenConfigure.class);

    /**
     * 免登录阅读白名单（A6 有声公开阅读接口，仅 GET 有效）：
     * 帖子/课题/调研列表与详情、调研问题、评论列表、榜单、课题成员；
     * 值机助手（onlineHelper2）：知识路径树、文章列表、文章详情（新窗口打卡）。
     * 注意：/api/cyt/article/* 只匹配一级路径，article/manage/{id}（编辑回显）不在白名单内；
     * helper/article/list（管理列表）即使被路径覆盖，也会被方法级 @SaCheckPermission 拦截。
     * 写操作（评论/点赞/收藏/发布/答卷/加入课题）与个人数据接口
     * （我的文章/我的收藏/消息/进度/收藏列表）仍需登录。
     */
    private static final String[] PUBLIC_READ_PATHS = {
            "/api/cyt/articleList",
            "/api/cyt/articleList/*",
            "/api/cyt/articleList/*/*",
            "/api/cyt/article/*",
            "/api/cyt/questions/*",
            "/api/cyt/comment/list/*",
            "/api/cyt/comment/rank",
            "/api/cyt/weekly/rank",
            "/api/cyt/stageList",
            "/api/cyt/member/*",
            "/api/helper/tree/tree",
            "/api/helper/article/font/list",
            "/api/helper/article/*"
    };
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private static boolean isPublicReadPath(String path) {
        for (String pattern : PUBLIC_READ_PATHS) {
            if (PATH_MATCHER.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

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
                    // 免登录阅读：仅放行白名单内的 GET 只读接口（评论/点赞/收藏/发布等写操作仍需登录）
                    .check(r -> {
                        String reqPath = SaHolder.getRequest().getRequestPath();
                        String reqMethod = SaHolder.getRequest().getMethod();
                        boolean readOnlyGet = "GET".equals(reqMethod) && isPublicReadPath(reqPath);
                        if (!readOnlyGet) {
                            StpUtil.checkLogin();
                        }
                    });
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
