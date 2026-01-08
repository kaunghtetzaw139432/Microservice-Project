package com.auth.service;

import java.text.SimpleDateFormat;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.models.JwtTokenResponse;
import com.auth.models.User;
import com.auth.models.UserDto;
import com.auth.repos.UserRepo;
import com.auth.utils.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public UserDto saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User saveUser = userRepo.save(user);
    return new UserDto(
        saveUser.getId(),
        saveUser.getEmail(),
        saveUser.getUsername(),
        saveUser.getRoles());
  }

 public JwtTokenResponse generateTokenResponse(String username) {
    String token = jwtUtil.generateToken(username);
    
    // DateTime ကို ပိုသပ်ရပ်တဲ့ format ပြောင်းခြင်း (Optional)
    String expiration = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(jwtUtil.getExpirationDate(token));

    return JwtTokenResponse.builder() // Lombok Builder သုံးလျှင်
            .token(token)
            .type("Bearer")
            .validUtil(expiration)
            .build();
}
}
