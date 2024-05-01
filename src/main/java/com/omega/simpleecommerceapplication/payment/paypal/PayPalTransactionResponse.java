package com.omega.simpleecommerceapplication.payment.paypal;

public record PayPalTransactionResponse(
        String id,
        String status
) {}
