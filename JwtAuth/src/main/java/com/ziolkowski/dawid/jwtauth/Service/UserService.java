package com.ziolkowski.dawid.jwtauth.Service;

import com.ziolkowski.dawid.jwtauth.Config.Auth.RegisterRequest;
import com.ziolkowski.dawid.jwtauth.Model.User;
import com.ziolkowski.dawid.jwtauth.Repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    private BCryptPasswordEncoder encoder;
    public User register(RegisterRequest request){
        User user =  User.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))
                .login("edekpl")
                .build();
        return userRepo.save(user);
    }
}
