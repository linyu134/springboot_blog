package com.lrm.intercdptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//配置类
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //addPathPatterns拦截admin下面所有的页面
        //excludePathPatterns排除页面
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");


    }
}
