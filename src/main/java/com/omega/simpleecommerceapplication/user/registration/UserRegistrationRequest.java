package com.omega.simpleecommerceapplication.user.registration;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
