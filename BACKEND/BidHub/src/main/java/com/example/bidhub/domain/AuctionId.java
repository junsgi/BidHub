package com.example.bidhub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class AuctionId  implements Serializable {
    @Column(length = 50)
    private String memId;

    @Column(length = 50)
    private String aitemId;

    @Builder
    public AuctionId(String memId, String aitemId) {
        this.memId = memId;
        this.aitemId = aitemId;
    }

    public AuctionId() {}
}
