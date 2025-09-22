package com.kookdonge.kookdonge_server.auth.service.client;

import com.kookdonge.kookdonge_server.auth.service.client.dto.res.GetUserInfoRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "googleClient",
        url = "https://openidconnect.googleapis.com"
)
public interface GoogleClient {


    @GetMapping("/v1/userinfo")
    GetUserInfoRes getUserInfo(@RequestHeader("Authorization") String authorization);
}
