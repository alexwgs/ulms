package com.cmbccd.ulms.common.config;

import com.cmbccd.ulms.websocket.service.LocalWsStateService;
import com.cmbccd.ulms.websocket.service.RedisWsStateService;
import com.cmbccd.ulms.websocket.service.WsStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * WebSocket 状态管理自动配置。
 * 根据 ulms.ws.multi-instance 属性选择实现：
 * - false（默认）：LocalWsStateService，使用 JVM 内 ConcurrentHashMap
 * - true：RedisWsStateService，使用 Redis 存储共享状态 + Pub/Sub 消息中继
 */
@Configuration
public class WsStateConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(WsStateConfiguration.class);

    /**
     * 单机模式（默认）。
     * matchIfMissing = true 确保不配置此属性时使用单机模式，兼容现有部署。
     */
    @Bean
    @ConditionalOnProperty(name = "ulms.ws.multi-instance", havingValue = "false", matchIfMissing = true)
    public WsStateService localWsStateService() {
        LOG.info("WebSocket 状态管理：单机模式（LocalWsStateService）");
        return new LocalWsStateService();
    }

    /**
     * 多实例模式。
     * 需要 Redis 可用。
     */
    @Bean
    @ConditionalOnProperty(name = "ulms.ws.multi-instance", havingValue = "true")
    public WsStateService redisWsStateService(StringRedisTemplate stringRedisTemplate,
                                              RedisConnectionFactory connectionFactory) {
        LOG.info("WebSocket 状态管理：多实例模式（RedisWsStateService）");

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.afterPropertiesSet();  // 必须先初始化订阅器，否则 addMessageListener 会报错
        container.start();

        return new RedisWsStateService(stringRedisTemplate, container);
    }
}
