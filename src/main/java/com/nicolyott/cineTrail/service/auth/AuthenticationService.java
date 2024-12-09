package com.nicolyott.cineTrail.service.auth;

import com.nicolyott.cineTrail.dto.AuthenticationDTO;
import com.nicolyott.cineTrail.entity.user.User;
import com.nicolyott.cineTrail.entity.user.UserRole;
import com.nicolyott.cineTrail.exception.user.UserAlreadyExistsException;
import com.nicolyott.cineTrail.infra.security.TokenService;
import com.nicolyott.cineTrail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public String login(AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public void register(String login, UserRole role, String password){
        if (userRepository.findByLogin(login) != null) {
            throw new UserAlreadyExistsException();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        User newUser = new User(login, encryptedPassword, role);
        userRepository.save(newUser);
    }

}
