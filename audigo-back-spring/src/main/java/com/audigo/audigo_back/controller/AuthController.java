package com.audigo.audigo_back.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audigo.audigo_back.dto.request.auth.SignInRequestDto;
import com.audigo.audigo_back.dto.request.auth.SignUpRequestDto;
import com.audigo.audigo_back.dto.response.auth.SignInResponseDto;
import com.audigo.audigo_back.dto.response.auth.SignUpResponseDto;
import com.audigo.audigo_back.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Log logger = LogFactory.getLog(AuthController.class);

    private final AuthService authService;

    /**
     * 회원가입
     * 
     * @param requestBody
     * @return
     */
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response;
    }

    /**
     * 로그인
     * 
     * @param requestBody
     * @return
     */
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        logger.info("=============== getEmail: " + requestBody.getEmail());

        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);

        return response;
    }

}
