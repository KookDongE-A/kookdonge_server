package com.kookdonge.kookdonge_server.common.info;

public class UserInfoStore {
    private static final ThreadLocal<UserInfo> userInfoStore = new ThreadLocal<>();

    public static void set(Long userId, Long clubId) {
        userInfoStore.set(UserInfo.of(userId, clubId));
    }
    public static Long getUserId(){
        return userInfoStore.get().getUserId();
    }

    public static Long getUserIdOrNull() {
        UserInfo userInfo = userInfoStore.get();
        return userInfo != null ? userInfo.getUserId() : null;
    }

    public static Long getClubId(){
        return userInfoStore.get().getClubId();
    }

    public static void remove() {
        userInfoStore.remove();
    }
}
