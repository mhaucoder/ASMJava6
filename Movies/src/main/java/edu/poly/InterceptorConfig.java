package edu.poly;

import edu.poly.services.DataInterceptorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    DataInterceptorService dataMovie;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dataMovie).addPathPatterns("/home", "/movie/**")
                .excludePathPatterns("/assets/**");
    }
}
