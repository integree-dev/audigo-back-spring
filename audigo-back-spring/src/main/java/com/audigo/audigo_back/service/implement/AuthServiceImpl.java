package com.audigo.audigo_back.service.implement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.audigo.audigo_back.dto.request.auth.SignInRequestDto;
import com.audigo.audigo_back.dto.request.auth.SignUpRequestDto;
import com.audigo.audigo_back.dto.response.ResponseDto;
import com.audigo.audigo_back.dto.response.auth.SignInResponseDto;
import com.audigo.audigo_back.dto.response.auth.SignUpResponseDto;
import com.audigo.audigo_back.entity.UserEntity;
import com.audigo.audigo_back.jwt.JWTUtil;
import com.audigo.audigo_back.repository.UserRepository;
import com.audigo.audigo_back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Log logger = LogFactory.getLog(AuthServiceImpl.class);

    private final UserRepository userRepository;
    // private final JwtProvider jwtProvider;
    private final JWTUtil jwtUtil;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            // not null 이나 duplicate check 진행
            String email = dto.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if (existedEmail)
                return SignUpResponseDto.duplicateEmail();

            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if (existedNickname)
                return SignUpResponseDto.duplicateNickname();

            String telNumber = dto.getTelNumber();
            boolean existedTelnumber = userRepository.existsByTelNumber(telNumber);
            if (existedTelnumber)
                return SignUpResponseDto.duplicateTelNumber();

            String password = dto.getPassword();
            String encodedPwd = passwordEncoder.encode(password);
            dto.setPassword(encodedPwd);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        String token = null;

        try {
            String email = dto.getEmail();
            logger.info("=== AuthServiceImpl signIn email: " + email);

            UserEntity userEntity = userRepository.findByEmail(email);

            if (userEntity == null)
                return SignInResponseDto.signInFail();

            logger.info("=== AuthServiceImpl userEntity : " + userEntity.toString());

            String password = dto.getPassword();
            String encodedPassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched)
                return SignInResponseDto.signInFail();

            // 1시간 = 60분 × 60초 × 1000밀리초 = 3,600,000 밀리초
            token = jwtUtil.createJwtWithEmail(email, 3600000L);
            logger.info("=== AuthServiceImpl token : " + token.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);
    }

}