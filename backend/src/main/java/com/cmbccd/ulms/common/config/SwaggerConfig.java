package com.cmbccd.ulms.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.setName("魏根生");
        contact.setUrl("http://blog.wei6130.cn/");
        contact.setEmail("weigensheng1203@cmbchina.com");

        return new OpenAPI()
                .info(new Info()
                        .title("A6广场API文档")
                        .description("Created by:WeiGenSehng")
                        .version("V1.0")
                        .contact(contact));
    }

    @Bean
    public GroupedOpenApi publicApi(Environment environment) {
        org.springframework.core.env.Profiles profiles =
                org.springframework.core.env.Profiles.of("dev", "uat");
        boolean enabled = environment.acceptsProfiles(profiles);

        return GroupedOpenApi.builder()
                .group("A6广场基础API")
                .pathsToMatch("/**")
                .build();
    }
}
