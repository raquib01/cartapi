package com.raquib.cartapi.controllers;

import com.raquib.cartapi.dtos.CheckoutRequest;
import com.raquib.cartapi.dtos.OrderDto;
import com.raquib.cartapi.services.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @Value("${stripe.webhook.secretKey}")
    private String stripeWebhookSecretKey;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout(@RequestBody @Valid CheckoutRequest request){

            var orderDto = orderService.checkout(request.getCartId());
            return ResponseEntity.ok(orderDto);


    }

    @PostMapping("/stripe-webhook")
    public ResponseEntity<Void> handleStripeWebhook(@RequestHeader("Stripe-Signature") String signature,
                                                    @RequestBody String payload){
        try{
            var event = Webhook.constructEvent(payload,signature,stripeWebhookSecretKey);

            var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);

            System.out.println(event.getType());
            if(event.getType().equals("checkout.session.completed")){
                Session session = (Session) stripeObject;
                System.out.println("sessionId: " + session.getId());
                System.out.println("paymentStatus: " + session.getPaymentStatus());
                var orderId = session.getMetadata().get("orderId");
                System.out.println("orderId: " + orderId);
                orderService.updateOrderStatus(orderId);

            }
            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(){
        var orders = orderService.getOrdersForCurrentUser();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId){
        var order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }
}
