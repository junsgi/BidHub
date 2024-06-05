package com.example.bidhub.payment;

import com.example.bidhub.domain.PaymentLog;
import com.example.bidhub.dto.PaymentLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    public List<PaymentLogResponse> getLog(String userId) {
        List<PaymentLogResponse> result = new LinkedList<>();
        List<PaymentLog> list = repository.findByMemId(userId);

        for(PaymentLog i : list) {
            String[] info = i.getOrderId().split("_");
            String a = i.getApprovedAt();
            if (a == null || a.isEmpty()) a = "취소됨";
            result.add(PaymentLogResponse.builder()
                            .TID(i.getPaymentLogId().getTid())
                            .approvedAt(a)
                            .order(info[0])
                            .amount(info[2]).build()
            );
        }
        return result;
    }
}
