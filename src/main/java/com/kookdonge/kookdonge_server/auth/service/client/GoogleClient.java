package com.kookdonge.kookdonge_server.auth.service.client;

import com.kookdonge.kookdonge_server.auth.service.client.dto.req.IssueAccessTokenByGrantCodeReq;
import com.kookdonge.kookdonge_server.auth.service.client.dto.res.GetUserInfoRes;
import com.kookdonge.kookdonge_server.auth.service.client.dto.res.IssueAccessTokenByGrantCodeRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "googleOAuthClient",
        url = "https://oauth2.googleapis.com"
)
public interface GoogleClient {

    @PostMapping("/token")
    IssueAccessTokenByGrantCodeRes issueAccessTokenByGrantCode(@RequestBody IssueAccessTokenByGrantCodeReq req);

    @GetMapping("/userinfo/v2/me")
    GetUserInfoRes getUserInfo(@RequestParam("access_token") String accessToken);
}
