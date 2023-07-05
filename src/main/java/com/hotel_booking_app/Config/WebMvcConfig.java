package com.hotel_booking_app.Config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/static/");
    }
}
