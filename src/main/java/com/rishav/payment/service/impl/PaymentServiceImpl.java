package com.rishav.payment.service.impl;

import com.rishav.payment.dto.PaymentRequest;
import com.rishav.payment.dto.PaymentResponseDto;
import com.rishav.payment.entity.PaymentResponse;
import com.rishav.payment.provider.MockPaymentProvider;
import com.rishav.payment.provider.PaymentProvider;
import com.rishav.payment.repository.PaymentResponseRepository;
import com.rishav.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentProvider paymentProvider;
    private final PaymentResponseRepository repository;

    @Override
    public PaymentResponseDto handlePayment(PaymentRequest request) {
        PaymentResponseDto response = paymentProvider.processPayment(request);

        PaymentResponse saved = PaymentResponse.builder()
                .transactionId(response.getTransactionId())
                .orderId(response.getOrderId())
                .status(response.getStatus())
                .message(response.getMessage())
                .build();

        repository.save(saved);
        return response;
    }

}
