package com.example.bidhub.payment;

import com.example.bidhub.domain.PaymentLog;
import com.example.bidhub.domain.PaymentLogId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentLog, PaymentLogId> {
}
