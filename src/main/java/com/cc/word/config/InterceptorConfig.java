package com.cc.word.config;

import com.cc.word.interceptor.CorsInterceptor;
import com.cc.word.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * 不⽤权限可以访问url /api/v1/pub/
 * 要登录可以访问url /api/v1/pri/
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("${prop.upload-img-folder}")
    private String UPLOAD_IMG_FOLDER;

    @Value("${prop.upload-music-folder}")
    private String UPLOAD_MUSIC_FOLDER;

    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    CorsInterceptor CorsInterceptor(){
        return new CorsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //跨域问题拦截请求向请求头中添加信息
        registry.addInterceptor(CorsInterceptor()).addPathPatterns("/**");

        //拦截全部
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/v1/pri/*/*/**")
                //不拦截哪些路径 斜杠⼀定要加
                .excludePathPatterns("/api/v1/pri/user/login","/api/v1/pri/user/register");
        WebMvcConfigurer.super.addInterceptors(registry);


    }

    //配置静态资源路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+UPLOAD_IMG_FOLDER);
        registry.addResourceHandler("/music/**").addResourceLocations("file:"+UPLOAD_MUSIC_FOLDER);
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}