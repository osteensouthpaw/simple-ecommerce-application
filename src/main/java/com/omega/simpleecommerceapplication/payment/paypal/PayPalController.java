package com.omega.simpleecommerceapplication.payment.paypal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payments/paypal")
@RequiredArgsConstructor
public class PayPalController {
    private final PayPalService payPalService;

    @PostMapping("checkout")
    public PayPalPaymentResponse createPayment(@RequestParam("orderId") Integer orderId) {
        return payPalService.createPayment(orderId);
    }


    @PostMapping("capture")
    public PayPalTransactionResponse completePayment(@RequestParam("token") String token) {
        return payPalService.completePayment(token);
    }

}

