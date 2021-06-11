package com.atblack_feather_official.netdesk.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.*;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

@Deprecated
//@Configuration
//@ConfigurationProperties("spring.datasource")
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        try {
            druidDataSource.setFilters("stat,wall");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/netdesk");




        try {
            druidDataSource.init();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return  druidDataSource;
    }

    /**
     * 配置druid监控
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){

        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/*");
        registrationBean.addInitParameter("loginUsername","admin");
        registrationBean.addInitParameter("loginPassword","admin");
        return registrationBean;
    }
    /**
     * 放置一个filter用于监控web请求
     *
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        WebStatFilter webStatFilter = new WebStatFilter();

        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>(webStatFilter);
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        registrationBean.addInitParameter("exclusions","*.js,*.gif,*.css,*.ico,*.mp4,*.jpg,*.png,/druid/*");

        return registrationBean;
    }


}
