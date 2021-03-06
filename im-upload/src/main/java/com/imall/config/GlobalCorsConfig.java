package com.imall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter(){

        //添加cors配置信息
        CorsConfiguration configuration = new CorsConfiguration();
        //1.添加允许的域名，不要写*,否则cookie就不能使用了
        configuration.addAllowedOrigin("http://manage.imall.com");
        //2.是否发送cookie信息
        configuration.setAllowCredentials(false);
        //3.允许的请求方式
        configuration.addAllowedMethod("POST");

        configuration.addAllowedMethod("OPTIONS");
        //4设置允许的头信息
        configuration.addAllowedHeader("*");
        //5.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**" ,configuration);
        //返回新的corsFilter
        return new CorsFilter(configurationSource);
    }

}
