package com.example.bidhub.payment;

import com.example.bidhub.dto.PaymentLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paymentLog")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;
    @GetMapping("/{userId}")
    public List<PaymentLogResponse> getLog(@PathVariable("userId") String userId) {
        return service.getLog(userId);
    }
}
