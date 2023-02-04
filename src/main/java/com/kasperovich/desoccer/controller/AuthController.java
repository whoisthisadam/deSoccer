package com.kasperovich.desoccer.controller;

import com.kasperovich.desoccer.dto.auth.AuthRequest;
import com.kasperovich.desoccer.dto.auth.AuthResponse;
import com.kasperovich.desoccer.repository.UserRepository;
import com.kasperovich.desoccer.security.jwt.JwtTokenHelper;
import com.kasperovich.desoccer.util.ValidCheck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@Tag(name = "Authentication")
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

        if(new ValidCheck().isEmailValid(request.getEmailOrLogin())){
            return ResponseEntity.ok(
                    AuthResponse.builder()
                            .login(userRepository
                                    .findUserByEmail(request.getEmailOrLogin())
                                    .orElseThrow(EntityNotFoundException::new)
                                    .getCredentials().getLogin())
                            .email(request.getEmailOrLogin())
                            .token(tokenUtils.generateToken(userProvider.loadUserByUsername(request.getEmailOrLogin())))
                            .roleName(userRepository
                                    .findUserByEmail(request.getEmailOrLogin())
                                    .orElseThrow(EntityNotFoundException::new).getRole().getName())
                            .build());
        }
        else{
            return ResponseEntity.ok(
                    AuthResponse.builder()
                            .login(request.getEmailOrLogin())
                            .email(userRepository
                                    .findUserByCredentialsLogin(request.getEmailOrLogin())
                                    .orElseThrow(EntityNotFoundException::new)
                                    .getEmail())
                            .token(tokenUtils.generateToken(userProvider.loadUserByUsername(request.getEmailOrLogin())))
                            .roleName(userRepository
                                    .findUserByCredentialsLogin(request.getEmailOrLogin())
                                    .orElseThrow(EntityNotFoundException::new).getRole().getName())
                            .build());
        }
    }
}
