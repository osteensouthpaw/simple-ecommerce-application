package com.omega.simpleecommerceapplication.user;

public record UserDto(
        Integer userId,
        String firstName,
        String lastName,
        String email,
        AppUserRole role,
        boolean isEnabled
) {
}
