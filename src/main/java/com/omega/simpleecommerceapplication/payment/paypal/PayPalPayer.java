package com.omega.simpleecommerceapplication.payment.paypal;

public record PayPalPayer(
        String payerId,
        String emailAddress,
        String fullName,
        String lastName
) {}