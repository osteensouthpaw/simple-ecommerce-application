package com.omega.simpleecommerceapplication.emailVerification;

import jakarta.mail.MessagingException;

public interface EmailSender {
    void sendEmail(String to, String name, String link);
}
