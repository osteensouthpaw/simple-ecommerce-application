package com.omega.simpleecommerceapplication.user.registration;

import com.omega.simpleecommerceapplication.user.UserDto;
import com.omega.simpleecommerceapplication.user.UserDtoMapper;
import com.omega.simpleecommerceapplication.user.jwtUtils.JwtService;
import com.omega.simpleecommerceapplication.user.token.ConfirmationTokenService;
import com.omega.simpleecommerceapplication.user.AppUser;
import com.omega.simpleecommerceapplication.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.omega.simpleecommerceapplication.user.AppUserRole.CUSTOMER;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final AppUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService tokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDtoMapper userDtoMapper;

    public void registerUser(UserRegistrationRequest request) {
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
    }

    public void confirmToken(String token) {
        tokenService.confirmToken(token);
    }

    public AuthenticationResponse login(UserAuthenticationRequest request) {
        Authentication authenticationToken = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        AppUser appUser = (AppUser) authenticationToken.getPrincipal();
        UserDto user = userDtoMapper.apply(appUser);
        String jwtToken = jwtService.generateToken(authenticationToken);
        return new AuthenticationResponse(user, jwtToken);
    }
}
