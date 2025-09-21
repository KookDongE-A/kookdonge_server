package com.kookdonge.kookdonge_server.auth.presentation;

import com.kookdonge.kookdonge_server.auth.presentation.dto.req.RegisterUserReq;
import com.kookdonge.kookdonge_server.auth.presentation.dto.req.ReissueAccessTokenReq;
import com.kookdonge.kookdonge_server.auth.presentation.dto.res.RegisterUserRes;
import com.kookdonge.kookdonge_server.auth.presentation.dto.res.ReissueAccessTokenRes;
import com.kookdonge.kookdonge_server.auth.service.UserService;
import com.kookdonge.kookdonge_server.auth.service.dto.RegisterUserDTO;
import com.kookdonge.kookdonge_server.common.RequestDTO;
import com.kookdonge.kookdonge_server.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserPresentation {

    private final UserService userService;

    @PostMapping("/api/users/me")
    public ResponseDTO<RegisterUserRes> registerUser(RequestDTO<RegisterUserReq> request) {
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
}
