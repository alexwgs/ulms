package com.cmbccd.ulms.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * SPA（单页应用）路由回退配置。
 *
 * <p>前端 Vue Router 使用 history 模式（createWebHistory），
 * 客户端路由如 /user/center 在后端没有对应的静态文件，
 * 需要将所有非 API、非静态资源的请求回退到 index.html，
 * 交给前端 Vue Router 处理。
 *
 * <p>规则：
 * <ul>
 *   <li>URL 带文件后缀（.js/.css/.png 等）→ 按静态资源正常处理</li>
 *   <li>URL 以 /ulms/api 开头 → 交给 Spring MVC 控制器</li>
 *   <li>其余 URL → 回退到 index.html（SPA 入口）</li>
 * </ul>
 *
 * @author WeiGenSheng
 */
@Configuration
public class SpaConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/")
                .resourceChain(true)
                .addResolver(new SpaPathResourceResolver());
    }

    /**
     * 自定义资源解析器：找不到对应文件时回退到 index.html。
     * 带文件后缀的请求（静态资源 404）不触发回退，保持浏览器报错语义。
     */
    private static class SpaPathResourceResolver extends PathResourceResolver {

        @Override
        protected Resource getResource(String resourcePath, Resource location) throws IOException {
            Resource resource = location.createRelative(resourcePath);

            // 文件存在 → 直接返回（正常静态资源）
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            // 文件不存在且无后缀 → SPA 路由，回退到 index.html
            if (!resourcePath.contains(".")) {
                Resource spaIndex = location.createRelative("index.html");
                if (spaIndex.exists() && spaIndex.isReadable()) {
                    return spaIndex;
                }
            }

            // 带后缀的文件不存在 → 返回 null（让浏览器正常显示 404）
            return null;
        }
    }
}
