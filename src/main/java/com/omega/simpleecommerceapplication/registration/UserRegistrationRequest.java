package com.omega.simpleecommerceapplication.registration;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
