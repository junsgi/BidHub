package com.example.bidhub.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PaymentLogResponse {
    private String TID;
    private String approvedAt;
    private String order;
    private String amount;

    @Builder
    public PaymentLogResponse(String TID, String approvedAt, String order, String amount) {
        this.TID = TID;
        this.approvedAt = approvedAt;
        this.order = order;
        this.amount = amount;
    }
}
