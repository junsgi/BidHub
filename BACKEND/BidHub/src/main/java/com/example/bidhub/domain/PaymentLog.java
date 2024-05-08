package com.example.bidhub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PaymentLog {
    @EmbeddedId
    private PaymentLogId PaymentLogId;

    @Column(length = 30)
    private String approvedAt;

    @Column(length = 100)
    private String orderId;
}
