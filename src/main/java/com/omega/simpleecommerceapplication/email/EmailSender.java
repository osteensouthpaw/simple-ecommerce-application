package com.omega.simpleecommerceapplication.email;

public interface EmailSender {
    void sendEmail(String to, String name, String link);
}
