package com.omega.simpleecommerceapplication.payment.paypal;

import com.omega.simpleecommerceapplication.order.OrderService;
import com.omega.simpleecommerceapplication.order.ShopOrder;
import com.omega.simpleecommerceapplication.payment.Payment;
import com.omega.simpleecommerceapplication.payment.PaymentRepository;
import com.omega.simpleecommerceapplication.user.AppUserService;
import com.omega.simpleecommerceapplication.user.UserDtoMapper;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayPalService {
    private final PaymentRepository paymentRepository;
    private final ApplicationContext applicationContext;
    private final PayPalHttpClient payPalHttpClient;
    public final OrderService orderService;
    public final AppUserService userService;
    private final UserDtoMapper userDtoMapper;

    public PayPalPaymentResponse createPayment(Integer orderId) {
        //find the order that is to be paid for
        ShopOrder shopOrder = orderService.findOrderById(orderId);
        //find the user to make the purchase
        Double orderTotal = shopOrder.getOrderTotal();
        //defining order requests and its properties
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        //amount with breakdown
        var amountWithBreakdown = new AmountWithBreakdown()
                .currencyCode("USD")
                .value(String.format("%.2f", orderTotal));

        var purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(amountWithBreakdown);


        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));
        orderRequest.applicationContext(applicationContext);

        var ordersCreateRequest = new OrdersCreateRequest()
                .requestBody(orderRequest);

        try {
            HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
            Order order = orderHttpResponse.result();

            String redirectUrl = order.links().stream()
                    .filter(link -> link.rel().equals("approve"))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new)
                    .href();

            Payment payment = Payment.builder()
                    .transactionId(order.id())
                    .paymentStatus(order.status())
                    .appUser(shopOrder.getAppUser())
                    .order(shopOrder)
                    .build();

            Payment savedPayment = paymentRepository.save(payment);

            return new PayPalPaymentResponse(
                    savedPayment.getPaymentId(),
                    order.id(),
                    userDtoMapper.apply(savedPayment.getAppUser()),
                    shopOrder.getOrderTotal(),
                    order.status(),
                    redirectUrl
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public PayPalTransactionResponse completePayment(String token) {
        var ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            Order order = orderHttpResponse.result();
            if (order.status() != null)
                return new PayPalTransactionResponse(
                        order.id(),
                        order.status()
                );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return new PayPalTransactionResponse("error", null);
    }
}

