package com.omega.simpleecommerceapplication.user.registration;

public record UserAuthenticationRequest(
        String email,
        String password
) {
}
