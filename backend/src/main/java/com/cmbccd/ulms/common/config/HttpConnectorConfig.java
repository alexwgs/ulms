package com.cmbccd.ulms.common.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置 Tomcat 同时支持 HTTP 和 HTTPS 双端口访问
 *
 * <p>HTTPS 端口由 server.port 控制（配置了 SSL 后默认即为 HTTPS）
 * <p>HTTP  端口由 server.http.port 控制（此配置项为自定义属性）
 *
 * @author WeiGenSheng
 */
@Configuration
public class HttpConnectorConfig {

    @Value("${server.http.port:8080}")
    private int httpPort;

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> httpConnectorCustomizer() {
        return factory -> {
            // 仅在 HTTP 端口与 HTTPS 端口不同时添加额外连接器（避免端口冲突）
            if (httpPort > 0 && httpPort != factory.getPort()) {
                Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
                connector.setScheme("http");
                connector.setPort(httpPort);
                // 将此连接器标记为 HTTP（不会自动重定向到 HTTPS）
                connector.setSecure(false);
                // 如果后续需要将 HTTP 自动跳转到 HTTPS，取消下面这行的注释：
                // connector.setRedirectPort(factory.getPort());
                factory.addAdditionalTomcatConnectors(connector);
            }
        };
    }
}
