package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.dto.AuthenticationDTO;
import com.nicolyott.cineTrail.dto.LoginResponseDTO;
import com.nicolyott.cineTrail.dto.RegisterDTO;
import com.nicolyott.cineTrail.entity.user.User;
import com.nicolyott.cineTrail.infra.security.TokenService;
import com.nicolyott.cineTrail.repository.UserRepository;
import com.nicolyott.cineTrail.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data) {
        String token = service.login(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
        try {
            service.register(data.login(), data.role(), data.password());
            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
