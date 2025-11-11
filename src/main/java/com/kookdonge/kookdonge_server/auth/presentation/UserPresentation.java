package com.kookdonge.kookdonge_server.auth.presentation;

import com.kookdonge.kookdonge_server.auth.presentation.dto.req.LoginReq;
import com.kookdonge.kookdonge_server.auth.presentation.dto.req.RegisterUserReq;
import com.kookdonge.kookdonge_server.auth.presentation.dto.req.ReissueAccessTokenReq;
import com.kookdonge.kookdonge_server.auth.presentation.dto.res.LoginRes;
import com.kookdonge.kookdonge_server.auth.presentation.dto.res.RegisterUserRes;
import com.kookdonge.kookdonge_server.auth.presentation.dto.res.ReissueAccessTokenRes;
import com.kookdonge.kookdonge_server.auth.presentation.dto.res.UserProfileRes;
import com.kookdonge.kookdonge_server.auth.service.UserService;
import com.kookdonge.kookdonge_server.auth.service.annotation.LoginRequired;
import com.kookdonge.kookdonge_server.auth.service.dto.LoginDTO;
import com.kookdonge.kookdonge_server.auth.service.dto.RegisterUserDTO;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubListRes;
import com.kookdonge.kookdonge_server.club.service.ClubService;
import com.kookdonge.kookdonge_server.common.dto.RequestDTO;
import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
import com.kookdonge.kookdonge_server.common.info.UserInfoStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="인증/인가")
@RequiredArgsConstructor
@RestController
public class UserPresentation {

    private final UserService userService;
    private final ClubService clubService;

    @Operation(summary = "회원가입")
    @PostMapping("/api/users/me")
    public ResponseDTO<RegisterUserRes> registerUser(@RequestBody RequestDTO<RegisterUserReq> request) {
        RegisterUserReq payload = request.getData();
        String googleGrantCode = payload.getGoogleGrantCode();
        String department = payload.getDepartment();
        String phoneNumber = payload.getPhoneNumber();
        String studentId = payload.getStudentId();

        RegisterUserDTO registerUserDTO = userService.registerUser(googleGrantCode, phoneNumber, department, studentId);
        return ResponseDTO.ok(RegisterUserRes.of(
                registerUserDTO.getExternalUserId(),
                registerUserDTO.getEmail(),
                registerUserDTO.getStudentId(),
                registerUserDTO.getPhoneNumber(),
                registerUserDTO.getDepartment(),
                registerUserDTO.getAccessToken(),
                registerUserDTO.getRefreshToken()
        ));
    }

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/api/users/me")
    @LoginRequired
    public ResponseDTO<UserProfileRes> getMyProfile() {
        Long userId = UserInfoStore.getUserId();

        UserProfileRes userInfo = userService.getUserInfo(userId);

        return ResponseDTO.ok(userInfo);
    }

    @Operation(summary = "내가 좋아요 누른 동아리 목록 조회")
    @GetMapping("/api/users/me/liked-clubs")
    @LoginRequired
    public ResponseDTO<List<ClubListRes>> getMyLikedClubs() {
        Long userId = UserInfoStore.getUserId();
        List<ClubListRes> likedClubs = clubService.getClubsLikedByUser(userId);
        return ResponseDTO.ok(likedClubs);
    }

    @PostMapping("/api/auth/reissue")
    public ResponseDTO<ReissueAccessTokenRes> reissueAccessTokenByRefreshToken(RequestDTO<ReissueAccessTokenReq> request) {
        ReissueAccessTokenReq data = request.getData();
        String refreshToken = data.getRefreshToken();

        String reissuedAccessToken = userService.reissueAccessTokenByRefreshToken(refreshToken);

        return ResponseDTO.ok(ReissueAccessTokenRes.of(reissuedAccessToken));
    }

    @PostMapping("/api/auth")
    public ResponseDTO<LoginRes> loginUser(RequestDTO<LoginReq> request) {
        LoginReq data = request.getData();
        String googleGrantCode = data.getGoogleGrantCode();

        LoginDTO loginUserDTO = userService.loginUser(googleGrantCode);

        return ResponseDTO.ok(LoginRes.of(
                loginUserDTO.getExternalUserId(),
                loginUserDTO.getEmail(),
                loginUserDTO.getStudentId(),
                loginUserDTO.getPhoneNumber(),
                loginUserDTO.getDepartment(),
                loginUserDTO.getAccessToken(),
                loginUserDTO.getRefreshToken()
        ));
    }
}
