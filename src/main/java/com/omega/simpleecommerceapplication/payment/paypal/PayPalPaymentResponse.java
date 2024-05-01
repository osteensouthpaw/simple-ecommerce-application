package com.omega.simpleecommerceapplication.payment.paypal;

import com.omega.simpleecommerceapplication.user.UserDto;

public record PayPalPaymentResponse(
        Integer paymentId,
        String TransactionId,
        UserDto user,
        Double totalPrice,
        String status,
        String redirectUrl
) {
}
