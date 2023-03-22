package com.ziolkowski.dawid.jwtauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziolkowski.dawid.jwtauth.Config.Auth.JwtUtils;
import com.ziolkowski.dawid.jwtauth.Config.Auth.LoginRequest;
import com.ziolkowski.dawid.jwtauth.Config.Auth.RegisterRequest;
import com.ziolkowski.dawid.jwtauth.Controller.AuthController;
import com.ziolkowski.dawid.jwtauth.Model.User;
import com.ziolkowski.dawid.jwtauth.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthControllerTest {

    private final UserService userService = Mockito.mock(UserService.class);
    private final JwtUtils jwtUtils = Mockito.mock(JwtUtils.class);
    private final AuthController authController = new AuthController(jwtUtils, userService, Mockito.mock(AuthenticationManager.class));
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    private final BCryptPasswordEncoder encoder = Mockito.mock(BCryptPasswordEncoder.class);

    @Test
    public void registerUserTest() throws Exception {
        RegisterRequest request = new RegisterRequest("test@test.com", "password");
        User user = User.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))
                .build();
        when(userService.register(any(RegisterRequest.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void authenticateAndGetTokenTest() throws Exception {
        // Mock AuthenticationManager and JwtUtils
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        JwtUtils jwtUtils = Mockito.mock(JwtUtils.class);
        AuthController authController = new AuthController(jwtUtils, userService, authenticationManager);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        // Create a LoginRequest object
        LoginRequest request = new LoginRequest("test@test.com", "password");

        // Mock authentication and JWT token generation
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateToken(anyString())).thenReturn("test_token");

        // Send a POST request to /auth/login with the LoginRequest object
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("test_token"));

        // Verify that authentication manager is called with correct argument
        ArgumentCaptor<UsernamePasswordAuthenticationToken> authRequestCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(authRequestCaptor.capture());
        UsernamePasswordAuthenticationToken authRequest = authRequestCaptor.getValue();
        assertEquals(request.email(), authRequest.getPrincipal());
        assertEquals(request.password(), authRequest.getCredentials().toString());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}