package com.omega.simpleecommerceapplication.user;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDtoMapper implements Function<AppUser, UserDto> {
    @Override
    public UserDto apply(AppUser user) {
        return new UserDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled()
        );
    }
}
