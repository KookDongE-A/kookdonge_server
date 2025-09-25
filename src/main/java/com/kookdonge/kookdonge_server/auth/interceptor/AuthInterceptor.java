package com.kookdonge.kookdonge_server.auth.interceptor;

import com.kookdonge.kookdonge_server.auth.common.AuthExceptionCode;
import com.kookdonge.kookdonge_server.auth.common.JWT;
import com.kookdonge.kookdonge_server.auth.service.UserService;
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
    private final UserService userService;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        if (isLoginRequired(handlerMethod)) {
            String externalUserId = extractExternalUserIdFromToken(request);
            Long userId = userService.getUserIdByExternalUserId(externalUserId);
            Long clubId = userService.getClubIdByUserId(userId);
            UserInfoStore.set(userId, clubId);
        }

        return true;
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
