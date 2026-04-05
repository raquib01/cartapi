package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.CheckoutSession;
import com.raquib.cartapi.entities.Order;
import com.raquib.cartapi.exceptions.PaymentException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePaymentGateway implements PaymentGateway{
    @Value("${websiteUrl}")
    private String websiteUrl;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try{

            var ScpBuilder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .putMetadata("orderId",order.getId().toString());


            order.getItems().forEach( item -> {
                var productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(item.getProduct().getName())
                    .build();

                    var priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("usd")
                    .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100))) // stripe wants in cents
                    .setProductData(productData)
                    .build();

                    var lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(Long.valueOf(item.getQuantity()))
                    .setPriceData(priceData)
                    .build();

                    ScpBuilder.addLineItem(lineItem);

        });

            var session = Session.create(ScpBuilder.build());

            return new CheckoutSession(session.getUrl());
        }catch (StripeException ex){
            System.out.println(ex.getMessage());
            throw new PaymentException();
        }
    }
}
