package com.decoticket.api.controller;

import com.decoticket.api.domain.login.LoginRequest;
import com.decoticket.api.domain.login.LoginResponse;
import com.decoticket.api.domain.user.User;
import com.decoticket.api.domain.user.UserRequestDTO;
import com.decoticket.api.repositories.UserRepository;
import com.decoticket.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(JwtEncoder jwtEncoder, UserService userService,
                          UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody UserRequestDTO body){
        User newUser = this.userService.createUser(body);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", security = @SecurityRequirement(name = ""))
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        var user = userRepository.findByEmail(loginRequest.email());

        if(user.isEmpty()){
            throw new BadCredentialsException("User not found!");
        }
        if(!user.get().isLoginCorrect(loginRequest, passwordEncoder)){
            throw  new BadCredentialsException("Incorrect password!");
        }
        
        var now = Instant.now();
        var expiresIn = 600L;

        var claims = JwtClaimsSet.builder()
                .issuer("myapi")
                .subject(String.valueOf(user.get().getId()))
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256")
                .keyId("deco-key-id")
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse(loginRequest.email(), jwtValue, expiresIn));
    }
}
