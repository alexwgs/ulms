package com.cmbccd.ulms.common.config;

import com.cmbccd.ulms.common.controller.MainController;
import com.cmbccd.ulms.common.exception.MainsiteErrorController;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 为所有 Controller 接口统一添加 /api 前缀。
 *
 * <p>背景：前端菜单配置的路由路径（如 /role、/menu、/department、/station）与后端接口路径冲突，
 * 生产环境下直接刷新/深链接这些页面会被后端接口拦截（返回 JSON/405），而不是 SPA 页面。
 * 给接口统一加 /api 前缀后，菜单路径不再与接口冲突，SPA 刷新/深链接恢复正常。
 *
 * <p>例外（保持原路径，不加前缀）：
 * <ul>
 *   <li>MainController：映射 "/" 返回 index.html（SPA 入口）；</li>
 *   <li>MainsiteErrorController：错误处理端点必须保持在 "/error"。</li>
 * </ul>
 */
@Configuration
public class ApiPathPrefixConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", clazz ->
                (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(RestController.class))
                        && !clazz.equals(MainController.class)
                        && !clazz.equals(MainsiteErrorController.class));
    }
}
