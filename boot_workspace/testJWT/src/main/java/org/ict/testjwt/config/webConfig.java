package org.ict.testjwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {
    // React, Vue, Angular 와 어플리케이션을 합쳐서 실행할 때, cross origin 문제 발생 처리가 목적임
    // 참고 : 하나의 웹 어플리케이션 구동시 port 한개로 구동이 원칙임
    // React 어플리케이션 port 에서 요청 <----> Boot 어플리케이션 port 응답

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .maxAge(3600);  // 1시간
    }

}
