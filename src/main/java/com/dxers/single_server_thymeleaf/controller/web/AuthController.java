package com.dxers.single_server_thymeleaf.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dxers.single_server_thymeleaf.dto.request.auth.SignInRequestDto;
import com.dxers.single_server_thymeleaf.dto.request.auth.SignUpRequestDto;
import com.dxers.single_server_thymeleaf.dto.response.ResponseCode;
import com.dxers.single_server_thymeleaf.service.AuthService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @GetMapping("sign-in")
    public String signIn(Model model) {
        
        if (!model.containsAttribute("signInForm")) 
            model.addAttribute("signInForm", new SignUpRequestDto());
        return "auth/sign-in";
    }
    
    @GetMapping("sign-up")
    public String signUp(Model model) {
        if (!model.containsAttribute("signUpForm")) 
            model.addAttribute("signUpForm", new SignUpRequestDto());
        return "auth/sign-up";
    }

    @PostMapping("sign-in")
    public String signInProcess(
        @Valid @ModelAttribute("signInForm") SignInRequestDto requestBody,
        BindingResult bindingResult,
        HttpSession session,
        Model model
    ) {
        if (bindingResult.hasErrors()) return "auth/sign-in";

        String result = authService.signIn(requestBody, session);
        if (!result.equals(ResponseCode.SUCCESS)) {
            model.addAttribute("errorMessage", result);
            return "auth/sign-in";
        }
        return "redirect:/home";
    }

    @PostMapping("sign-up")
    public String signUpProcess(
        @Valid @ModelAttribute("signUpForm") SignUpRequestDto requestBody,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) return "auth/sign-up";

        String result = authService.signUp(requestBody);
        if (!result.equals(ResponseCode.SUCCESS)) {
            model.addAttribute("errorMessage", result);
            return "auth/sign-up";
        }
        return "redirect:/auth/sign-in";
    }

}
