package com.decoticket.api.controller;

import com.decoticket.api.domain.login.LoginRequest;
import com.decoticket.api.domain.login.LoginResponse;
import com.decoticket.api.domain.role.Role;
import com.decoticket.api.domain.user.User;
import com.decoticket.api.domain.user.UserRequestDTO;
import com.decoticket.api.repositories.RoleRepository;
import com.decoticket.api.repositories.UserRepository;
import com.decoticket.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(JwtEncoder jwtEncoder, UserService userService,
                          UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRequestDTO body){

        var userFromDb = userRepository.findByEmail(body.email());
        var customerRole = roleRepository.findByName(Role.Values.CUSTOMER.name())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer role not found"));

        if(userFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User already exists!");
        }

        User newUser = new User();
        newUser.setFullName(body.fullName());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setIdentification(body.identification());
        newUser.setIsActive(body.isActive());
        newUser.setRoles(Set.of(customerRole));

        userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        var user = userRepository.findByEmail(loginRequest.email());

        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User not found!");
        }
        if(!user.get().isLoginCorrect(loginRequest, passwordEncoder)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Incorrect password!");
        }

        var now = Instant.now();
        var expiresIn = 600L;

        List<String> roles = user.get().getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        var claims = JwtClaimsSet.builder()
                .issuer("myapi")
                .subject(String.valueOf(user.get().getId()))
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .claim("r", roles)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256")
                .keyId("deco-key-id")
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse(loginRequest.email(), jwtValue, expiresIn));
    }
}
