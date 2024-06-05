package com.example.bidhub.payment;

import com.example.bidhub.domain.PaymentLog;
import com.example.bidhub.domain.PaymentLogId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentLog, PaymentLogId> {
    @Query(value = "SELECT * FROM payment_log pl WHERE pl.mem_id = :memId ORDER BY pl.approved_at DESC", nativeQuery = true)
    List<PaymentLog> findByMemId(@Param("memId") String memId);
}
