package com.dxers.single_server_thymeleaf.service;

import com.dxers.single_server_thymeleaf.dto.request.auth.SignInRequestDto;
import com.dxers.single_server_thymeleaf.dto.request.auth.SignUpRequestDto;

import jakarta.servlet.http.HttpSession;

public interface AuthService {
    String signIn (SignInRequestDto dto, HttpSession session);
    String signUp (SignUpRequestDto dto);
}
