package com.example.bidhub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class AuctionId  implements Serializable {
    @Column(length = 50)
    private String memId;

    @Column(length = 50)
    private String aitemId;
}
