package com.omega.simpleecommerceapplication.registration;

import com.omega.simpleecommerceapplication.token.ConfirmationTokenService;
import com.omega.simpleecommerceapplication.user.AppUser;
import com.omega.simpleecommerceapplication.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.omega.simpleecommerceapplication.user.AppUserRole.CUSTOMER;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final AppUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService tokenService;

    public String registerUser(UserRegistrationRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        AppUser appUser = AppUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(encodedPassword)
                .isEnabled(false)
                .role(CUSTOMER)
                .build();

        appUser = userService.createUser(appUser);
        if (!userService.existsByEmail(appUser.getUsername()))
            tokenService.sendVerificationEmail(appUser);
        return "check your email";
    }

    public void confirmToken(String token) {
        tokenService.confirmToken(token);
    }
}
