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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth API", description = "사용자 권한 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Log logger = LogFactory.getLog(AuthController.class);

    private final AuthService authService;

    /**
     * 회원가입
     * @param requestBody
     * @return
     */
    @Operation(summary = "최초 회원가입", description = "최초 회원가입 시 사용자 정보를 등록.")
    @ApiResponses({
        @ApiResponse(responseCode = "SU", description = "가입성공"),
        @ApiResponse(responseCode = "DE", description = "DUPLICATE_EMAIL"),
        @ApiResponse(responseCode = "DN", description = "DUPLICATE_NICKNAME"),
        @ApiResponse(responseCode = "DT", description = "DUPLICATE_TEL_NUMBER")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response;
    }

    
    @Operation(summary = "로그인", description = "로그인 시 사용자 mail 정보를 전달.")
    @ApiResponses({
        @ApiResponse(responseCode = "SU", description = "로그인 성공"),
        @ApiResponse(responseCode = "401", description = "SIGN_IN_FAIL")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        logger.info("=============== getEmail: " + requestBody.getEmail());

        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);

        return response;
    }

}
