package com.kookdonge.kookdonge_server.auth.service.client;

import com.kookdonge.kookdonge_server.auth.service.client.dto.req.IssueAccessTokenByGrantCodeReq;
import com.kookdonge.kookdonge_server.auth.service.client.dto.res.IssueAccessTokenByGrantCodeRes;
import com.kookdonge.kookdonge_server.common.config.FeignFormConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "googleOAuthClient",
        url = "https://oauth2.googleapis.com",
        configuration = FeignFormConfig.class
)
public interface GoogleOAuthClient {

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    IssueAccessTokenByGrantCodeRes issueAccessTokenByGrantCode(IssueAccessTokenByGrantCodeReq req);

}
