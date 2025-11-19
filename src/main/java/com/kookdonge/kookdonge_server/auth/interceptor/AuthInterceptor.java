package com.kookdonge.kookdonge_server.auth.interceptor;

import com.kookdonge.kookdonge_server.auth.common.AuthExceptionCode;
import com.kookdonge.kookdonge_server.auth.common.JWT;
import com.kookdonge.kookdonge_server.auth.service.UserInfoProvider;
import com.kookdonge.kookdonge_server.auth.service.annotation.LoginRequired;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import com.kookdonge.kookdonge_server.common.info.UserInfoStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JWT jwt;
    private final UserInfoProvider userInfoProvider;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        String token = extractTokenIfExists(request);
        boolean hasValidToken = token != null && jwt.isTokenValid(token);

        // 토큰이 있으면 UserInfoStore 설정
        if (hasValidToken) {
            String externalUserId = jwt.extractUserId(token);
            Long userId = userInfoProvider.getUserIdByExternalUserId(externalUserId);
            Long clubId = userInfoProvider.getClubIdByUserId(userId);
            UserInfoStore.set(userId, clubId);
        }

        // @LoginRequired인데 토큰이 없거나 유효하지 않으면 예외
        if (isLoginRequired(handlerMethod) && !hasValidToken) {
            throw new CustomException(AuthExceptionCode.NOT_FOUND_AUTH_HEADER);
        }

        return true;
    }

    private String extractTokenIfExists(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    private boolean isLoginNotRequired(HandlerMethod handlerMethod) {
        return !isLoginRequired(handlerMethod);
    }

    private boolean isLoginRequired(HandlerMethod handlerMethod) {
        LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);

        return !Objects.isNull(loginRequired);
    }

    private String extractExternalUserIdFromToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new CustomException(AuthExceptionCode.NOT_FOUND_AUTH_HEADER);
        }

        String token = authorizationHeader.substring(7);

        if (!jwt.isTokenValid(token)) {
            throw new CustomException(AuthExceptionCode.INVALID_TOKEN);
        }

        return jwt.extractUserId(token);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoStore.remove();
    }
}
