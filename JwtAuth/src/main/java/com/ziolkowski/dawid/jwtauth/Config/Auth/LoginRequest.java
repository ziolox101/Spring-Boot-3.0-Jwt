package com.ziolkowski.dawid.jwtauth.Config.Auth;

public record LoginRequest(
        String email,
        String password
) {
}
