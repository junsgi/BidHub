package com.example.bidhub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class PaymentLogId  implements Serializable {
    @Column(length = 50)
    private String memId;

    @Column(length = 50)
    private String tid;
}
