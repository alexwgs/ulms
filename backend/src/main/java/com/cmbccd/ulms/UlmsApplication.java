package com.cmbccd.ulms;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author WeiGenSheng
 * @Time 2020年5月4日 上午12:43:03
 * @Version 1.0 Description:
 */
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.cmbccd.ulms.*.dao")
@SpringBootApplication
@RestController
// public class UlmsApplication extends SpringBootServletInitializer {
// protected SpringApplicationBuilder configure(SpringApplicationBuilder
// builder) {
// return builder.sources(UlmsApplication.class);
// }
// }

public class UlmsApplication {
    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(UlmsApplication.class, args);
    }
}