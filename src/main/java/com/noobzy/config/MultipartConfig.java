package com.noobzy.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大100M
        factory.setMaxFileSize(DataSize.of(1000, DataUnit.MEGABYTES));
        // 设置总上传数据总大小1000M
        factory.setMaxRequestSize(DataSize.of(2000, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

}
