package com.omega.simpleecommerceapplication.token;

import com.omega.simpleecommerceapplication.emailVerification.EmailService;
import com.omega.simpleecommerceapplication.exceptions.DuplicateResourceException;
import com.omega.simpleecommerceapplication.exceptions.InvalidConfirmationTokenException;
import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import com.omega.simpleecommerceapplication.user.AppUser;
import com.omega.simpleecommerceapplication.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository tokenRepository;
    private final EmailService emailService;

    public void sendVerificationEmail(AppUser appUser) {
        String token = generateConfirmationToken();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusSeconds(15))
                .appUser(appUser)
                .build();

        ConfirmationToken savedToken = tokenRepository.save(confirmationToken);
        String link = "http://localhost:8080/api/v1/users/confirm?token=" + savedToken.getToken();

        emailService.sendEmail(appUser.getEmail(), appUser.getFirstName(), link);
    }


    @Transactional(dontRollbackOn = InvalidConfirmationTokenException.class)
    public void confirmToken(String token) {
        ConfirmationToken userToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid token"));

        AppUser appUser = userToken.getAppUser();

        if (userToken.getConfirmedAt() != null || appUser.isEnabled()) {
            throw new DuplicateResourceException("account already verified");
        }

        if (userToken.getExpiresAt().isBefore(LocalDateTime.now()) && !appUser.isEnabled()) {
            sendVerificationEmail(appUser);
            throw new InvalidConfirmationTokenException("token is expired. A new verification token will be sent to your email");
        }

        userToken.setConfirmedAt(LocalDateTime.now());
        tokenRepository.save(userToken);
        appUser.setEnabled(true);
        tokenRepository.save(userToken);
    }


    private String generateConfirmationToken() {
        return UUID.randomUUID().toString();
    }
}
