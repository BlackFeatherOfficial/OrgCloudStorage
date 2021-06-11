package com.atblack_feather_official.netdesk;

import com.atblack_feather_official.netdesk.config.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})

public class NetdeskApplication {
    public static void main(String[] args) {
        SpringApplication.run(NetdeskApplication.class, args);
    }

}
