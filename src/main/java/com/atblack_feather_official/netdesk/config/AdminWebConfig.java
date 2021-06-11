package com.atblack_feather_official.netdesk.config;

import com.atblack_feather_official.netdesk.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/favicon.ico","/invent","/addUserToSession","/registerBack","/indexTree","/QueryIndexTree","/register&back","/linkList_json","/checkLinkPassword","/linkPassword","/static/**","/checkLinkExist","/login_back","/link","/","/login","/sql","/CheckUserExists","/CheckUserPassword","/register","/test");
    }
}
