package com.kookdonge.kookdonge_server.auth.service.client.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = lombok.AccessLevel.PRIVATE)
public class IssueAccessTokenByGrantCodeReq {
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String grant_type;

    public static IssueAccessTokenByGrantCodeReq of(String code, String clientId, String clientSecret, String redirectUri) {
        return IssueAccessTokenByGrantCodeReq.builder()
                .code(code)
                .client_id(clientId)
                .client_secret(clientSecret)
                .redirect_uri(redirectUri)
                .grant_type("authorization_code")
                .build();
    }
}
