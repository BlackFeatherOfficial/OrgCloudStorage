package com.atblack_feather_official.netdesk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class NetdeskApplicationTests {

    @Autowired
    DataSource dataSource;


    @Test
    public void contextLoads() {
        Integer a = 103379;
        Integer b = 1024*1024;
        System.out.println((double)a/b);
    }

}
