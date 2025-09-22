package com.kookdonge.kookdonge_server.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.kookdonge.kookdonge_server")
public class OpenFeignConfig {
}
