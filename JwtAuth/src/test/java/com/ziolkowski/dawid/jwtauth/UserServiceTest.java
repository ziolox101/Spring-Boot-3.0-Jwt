package com.ziolkowski.dawid.jwtauth;
import com.ziolkowski.dawid.jwtauth.Config.Auth.RegisterRequest;
import com.ziolkowski.dawid.jwtauth.Model.User;
import com.ziolkowski.dawid.jwtauth.Repo.UserRepo;
import com.ziolkowski.dawid.jwtauth.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest("test@test.com", "password");

        when(encoder.encode(any())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(request.email());
        savedUser.setPassword("encodedPassword");
        savedUser.setLogin("edekpl");

        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        User result = userService.register(request);

        assert result.getId() == savedUser.getId();
        assert result.getEmail().equals(savedUser.getEmail());
        assert result.getPassword().equals(savedUser.getPassword());
        assert result.getLogin().equals(savedUser.getLogin());
    }
}