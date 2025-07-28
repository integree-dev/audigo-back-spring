package com.audigo.audigo_back.service.implement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.audigo.audigo_back.dto.response.ResponseDto;
import com.audigo.audigo_back.dto.response.user.GetSignInUserResponseDto;
import com.audigo.audigo_back.entity.UserEntity;
import com.audigo.audigo_back.repository.UserRepository;
import com.audigo.audigo_back.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {

        UserEntity userEntity = null;

        try {

            userEntity = userRepository.findByEmail(email);
            logger.info("=== UserServiceImpl findByEmail : " + email);

            if (userEntity == null)
                return GetSignInUserResponseDto.noMatchedUser();

            logger.info("=== UserServiceImpl findByEmail result : " + userEntity.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInUserResponseDto.success(userEntity);
    }

}