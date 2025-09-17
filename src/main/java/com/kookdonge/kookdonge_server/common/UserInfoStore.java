package com.kookdonge.kookdonge_server.common;

public class UserInfoStore {
    private static final ThreadLocal<UserInfo> userInfoStore = new ThreadLocal<>();

    public static void set(Long userId) {
        userInfoStore.set(UserInfo.of(userId));
    }

    public static void remove() {
        userInfoStore.remove();
    }
}
