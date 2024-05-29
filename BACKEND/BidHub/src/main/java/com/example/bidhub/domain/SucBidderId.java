package com.example.bidhub.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Embeddable
@Data
public class SucBidderId implements Serializable {
    @JoinColumn(name = "aitem_id")
    @OneToOne(fetch = FetchType.EAGER)
    private AuctionItem aitemId;

    @JoinColumn(name = "mem_id")
    @OneToOne
    private Member memId;
}
