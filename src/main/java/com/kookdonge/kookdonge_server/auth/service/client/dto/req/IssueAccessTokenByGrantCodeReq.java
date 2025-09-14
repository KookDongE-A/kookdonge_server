package com.kookdonge.kookdonge_server.auth.service.client.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = lombok.AccessLevel.PRIVATE)
public class IssueAccessTokenByGrantCodeReq {
    private String code;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonProperty("redirect_uri")
    private String redirectUri;
    @JsonProperty("grant_type")
    private String grantType;

    public static IssueAccessTokenByGrantCodeReq fromGrantCode(String code, String clientId, String clientSecret, String redirectUri) {
        return IssueAccessTokenByGrantCodeReq.builder()
                .code(code)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .grantType("authorization_code")
                .build();
    }
}
