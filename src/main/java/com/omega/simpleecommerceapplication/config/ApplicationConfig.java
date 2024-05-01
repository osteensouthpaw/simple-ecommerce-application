package com.omega.simpleecommerceapplication.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    private String clientId = "AfX54hTlXP9FlearK7zzA-1qSKbNavB0rPc_k-WNenaZYAdYPMnd7hd6scrrNYO1M9njEO_awLuzNEs7";
    private String clientSecret = "EDsf_268_38ilu-KCwM885j2sGR1rkYnvJ36ZwvZ7m36r_tDeoqzpKnt6CUCNUKOiaQFW3_wOCGzXgIA";


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(1025);
        mailSender.setUsername("admin@gmail.com");
        mailSender.setPassword("admin");
        return mailSender;
    }

    @Bean
    public PayPalEnvironment payPalEnvironment() {
        return new PayPalEnvironment.Sandbox(clientId, clientSecret);
    }

    @Bean
    public PayPalHttpClient payPalHttpClient(PayPalEnvironment payPalEnvironment) {
        return new PayPalHttpClient(payPalEnvironment);
    }

    @Bean
    public ApplicationContext applicationContext() {
        return new ApplicationContext()
                .brandName("ecommerce system")
                .returnUrl("https://localhost:8080/api/v1/payments/paypal/capture")
                .cancelUrl("https://localhost:8080/api/v1/payments/paypal/cancel");
    }
}
