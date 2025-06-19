package com.dxers.single_server_thymeleaf.service.implement;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dxers.single_server_thymeleaf.dto.request.auth.SignInRequestDto;
import com.dxers.single_server_thymeleaf.dto.request.auth.SignUpRequestDto;
import com.dxers.single_server_thymeleaf.dto.response.ResponseCode;
import com.dxers.single_server_thymeleaf.entity.UserEntity;
import com.dxers.single_server_thymeleaf.repository.UserRepository;
import com.dxers.single_server_thymeleaf.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signIn(SignInRequestDto dto, HttpSession session) {
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();

        try {
            UserEntity userEntity = userRepository.findByUserEmail(userEmail);
            if (userEntity == null) return ResponseCode.SIGN_IN_FAILED;

            String encodedPassword = userEntity.getUserPassword();
            boolean isPasswordMatched = passwordEncoder.matches(userPassword, encodedPassword);
            if (!isPasswordMatched) return ResponseCode.SIGN_IN_FAILED;

            session.setAttribute("userEmail", userEmail);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseCode.DATABASE_ERROR;
        }

        return ResponseCode.SUCCESS;
    }

    @Override
    public String signUp(SignUpRequestDto dto) {
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();

        try {
            boolean isExistUserEmail = userRepository.existsByUserEmail(userEmail);
            if (isExistUserEmail) return ResponseCode.DUPLICATED_EMAIL;

            String encodedPassword = passwordEncoder.encode(userPassword);
            dto.setUserPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);
        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseCode.DATABASE_ERROR;
        }

        return ResponseCode.SUCCESS;
    }
    
}
