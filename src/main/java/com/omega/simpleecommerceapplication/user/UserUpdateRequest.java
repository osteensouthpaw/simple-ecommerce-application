package com.omega.simpleecommerceapplication.user;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String password,
        String email
) {
}
