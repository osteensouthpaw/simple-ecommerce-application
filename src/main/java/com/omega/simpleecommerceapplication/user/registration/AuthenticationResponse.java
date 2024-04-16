package com.omega.simpleecommerceapplication.user.registration;

import com.omega.simpleecommerceapplication.user.AppUser;
import com.omega.simpleecommerceapplication.user.UserDto;

public record AuthenticationResponse(
        UserDto userDto,
        String jwtToken
) {
}
