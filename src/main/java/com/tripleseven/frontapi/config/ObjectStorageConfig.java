package com.tripleseven.frontapi.config;

import com.tripleseven.frontapi.service.ObjectStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectStorageConfig {

    @Bean
    public ObjectStorageService objectStorageService() {
        return new ObjectStorageService("https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_c20e3b10d61749a2a52346ed0261d79e");
    }
}
