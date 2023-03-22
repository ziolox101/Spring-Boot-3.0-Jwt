package com.ziolkowski.dawid.jwtauth.Config.Auth;

public record RegisterRequest(
        String email,
        String password
) {
}
