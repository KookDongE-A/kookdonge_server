package com.kookdonge.kookdonge_server.auth.presentation;

import com.kookdonge.kookdonge_server.auth.presentation.dto.req.RegisterUserDTO;
import com.kookdonge.kookdonge_server.auth.service.UserService;
import com.kookdonge.kookdonge_server.common.RequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserPresentation {

    private final UserService userService;

    @PostMapping("/api/users/me")
    public void registerUser(RequestDTO<RegisterUserDTO> request) {
        RegisterUserDTO payload = request.getData();
        String googleGrantCode = payload.getGoogleGrantCode();

//        userService.registerUser(go)
    }
}
