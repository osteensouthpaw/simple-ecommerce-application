package com.omega.simpleecommerceapplication.user.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationRequest request) {
        registrationService.registerUser(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserAuthenticationRequest request) {
        var authenticationResponse = registrationService.login(request);
        return ResponseEntity
                .accepted()
                .body(authenticationResponse);
    }
}
