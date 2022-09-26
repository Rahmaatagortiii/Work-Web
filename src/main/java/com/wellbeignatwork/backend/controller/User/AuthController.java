package com.wellbeignatwork.backend.controller.User;


import com.wellbeignatwork.backend.dto.*;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.exceptions.UserExceptions.UserAlreadyExistAuthenticationException;
import com.wellbeignatwork.backend.repository.User.PasswordTokenRepository;
import com.wellbeignatwork.backend.security.jwt.TokenProvider;
import com.wellbeignatwork.backend.service.UserService.MailService;
import com.wellbeignatwork.backend.service.UserService.PasswordResetService;
import com.wellbeignatwork.backend.service.UserService.UserService;
import com.wellbeignatwork.backend.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    MailService mailService;

    @Autowired
    PasswordResetService passwordResetService;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            User user = userService.registerNewUser(signUpRequest);
        } catch (UserAlreadyExistAuthenticationException e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email) {
        passwordResetService.forgetPassword(email);
        return ResponseEntity.ok("reset password sent successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> savePassword(@Valid @RequestBody PasswordReset passwordDto, @RequestParam String token) {
        String result = passwordResetService.validatePasswordResetToken(token);
        if (result != null) {
            return ResponseEntity
                    .badRequest()
                    .body("Error Token is : " + result);
        }

        User user = passwordTokenRepository.findByToken(token).getUser();
        if (user.getEmail() != null) {
            passwordResetService.changeUserPassword(user, passwordDto.getNewPassword());
            passwordTokenRepository.delete(passwordTokenRepository.findByToken(token));
            return ResponseEntity.ok("Password changed successfully!");

        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error Token is : " + result);
        }
    }
}