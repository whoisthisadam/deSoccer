package com.kasperovich.controller;

import com.kasperovich.dto.auth.AuthRequest;
import com.kasperovich.dto.auth.AuthResponse;
import com.kasperovich.repository.UserRepository;
import com.kasperovich.security.UserSecurityService;
import com.kasperovich.security.jwt.JwtTokenHelper;
import com.kasperovich.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication controller")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenHelper tokenUtils;

    private final UserDetailsService userProvider;

    private final UserRepository userRepository;




    @Operation(
            summary = "Login user in system",
            description = "Return Auth-Token with user login",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful authorization",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Request error", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            })
    @PostMapping
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {

        Authentication authenticate =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmailOrLogin(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        if(UserSecurityService.isValid(request.getEmailOrLogin())){
            return ResponseEntity.ok(
                    AuthResponse.builder()
                            .login(userRepository
                                    .findUserByEmail(request.getEmailOrLogin())
                                    .orElseThrow(RuntimeException::new)
                                    .getCredentials().getLogin())
                            .email(request.getEmailOrLogin())
                            .token(tokenUtils.generateToken(userProvider.loadUserByUsername(request.getEmailOrLogin())))
                            .build());
        }
        else{
            return ResponseEntity.ok(
                    AuthResponse.builder()
                            .login(request.getEmailOrLogin())
                            .email(userRepository
                                    .findUserByCredentialsLogin(request.getEmailOrLogin())
                                    .orElseThrow(RuntimeException::new)
                                    .getEmail())
                            .token(tokenUtils.generateToken(userProvider.loadUserByUsername(request.getEmailOrLogin())))
                            .build());
        }
    }
}
