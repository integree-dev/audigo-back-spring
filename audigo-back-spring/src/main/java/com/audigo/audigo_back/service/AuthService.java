package com.audigo.audigo_back.service;

import org.springframework.http.ResponseEntity;

import com.audigo.audigo_back.dto.request.auth.SignInRequestDto;
import com.audigo.audigo_back.dto.request.auth.SignUpRequestDto;
import com.audigo.audigo_back.dto.response.auth.SignInResponseDto;
import com.audigo.audigo_back.dto.response.auth.SignUpResponseDto;

public interface AuthService {

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
