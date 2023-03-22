package com.ziolkowski.dawid.jwtauth.Controller;

import com.ziolkowski.dawid.jwtauth.Config.Auth.JwtUtils;
import com.ziolkowski.dawid.jwtauth.Config.Auth.LoginRequest;
import com.ziolkowski.dawid.jwtauth.Config.Auth.LoginResponse;
import com.ziolkowski.dawid.jwtauth.Config.Auth.RegisterRequest;
import com.ziolkowski.dawid.jwtauth.Model.User;
import com.ziolkowski.dawid.jwtauth.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private JwtUtils jwtUtils;
    private UserService service;
    private AuthenticationManager authenticationManager;
    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterRequest userInfo) {
        return service.register(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        if (authentication.isAuthenticated()) {
           return ResponseEntity.ok(new LoginResponse(jwtUtils.generateToken(authRequest.email())));
        } else {
            return  ResponseEntity.badRequest().body("Wrong Credentials");
        }
    }
}
