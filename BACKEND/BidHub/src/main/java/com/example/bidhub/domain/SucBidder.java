package com.example.bidhub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SucBidder {
    @Id
    @Column(length = 50)
    private String sbId;

    @ManyToOne
    @JoinColumn(name = "memId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "aitemId")
    private AuctionItem auctionItem;
}
