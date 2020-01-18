package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

    @Bean
    public DeviceResolverHandlerInterceptor
    deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    @Bean
    public SitePreferenceHandlerInterceptor
    sitePreferenceHandlerInterceptor() {
        return new SitePreferenceHandlerInterceptor();
    }

    @Bean
    public SitePreferenceHandlerMethodArgumentResolver
    sitePreferenceHandlerMethodArgumentResolver() {
        return new SitePreferenceHandlerMethodArgumentResolver();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(deviceResolverHandlerInterceptor());
        registry.addInterceptor(sitePreferenceHandlerInterceptor());
    }
    @Override
    public void addArgumentResolvers(
            final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(sitePreferenceHandlerMethodArgumentResolver());
    }

}
