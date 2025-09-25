package com.kookdonge.kookdonge_server.auth.presentation;

import com.kookdonge.kookdonge_server.auth.presentation.dto.req.LoginReq;
import com.kookdonge.kookdonge_server.auth.presentation.dto.req.RegisterUserReq;
import com.kookdonge.kookdonge_server.auth.presentation.dto.req.ReissueAccessTokenReq;
import com.kookdonge.kookdonge_server.auth.presentation.dto.res.LoginRes;
import com.kookdonge.kookdonge_server.auth.presentation.dto.res.RegisterUserRes;
import com.kookdonge.kookdonge_server.auth.presentation.dto.res.ReissueAccessTokenRes;
import com.kookdonge.kookdonge_server.auth.service.UserService;
import com.kookdonge.kookdonge_server.auth.service.dto.LoginDTO;
import com.kookdonge.kookdonge_server.auth.service.dto.RegisterUserDTO;
import com.kookdonge.kookdonge_server.common.dto.RequestDTO;
import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="인증/인가")
@RequiredArgsConstructor
@RestController
public class UserPresentation {

    private final UserService userService;

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
