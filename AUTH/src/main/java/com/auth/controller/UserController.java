package com.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.exception.UsernameNotFoundException;
import com.auth.models.JwtTokenResponse;
import com.auth.models.LoginRequest;
import com.auth.models.User;
import com.auth.models.UserDto;
import com.auth.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User user) {
        UserDto userDto = userService.saveUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }

    @PostMapping("/generateToken")
    public JwtTokenResponse generateToken(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return userService.generateTokenResponse(request.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid Credentials");
        }
    }

}
