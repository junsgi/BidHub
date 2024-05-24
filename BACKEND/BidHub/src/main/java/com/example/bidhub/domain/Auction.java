package com.example.bidhub.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "AUCTION_SEQ",
        sequenceName = "AUCTION_SEQ",
        allocationSize = 1
)
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUCTION_SEQ")
    private Long auctionId;

    @ManyToOne
    @JoinColumn(name = "mem_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "aitem_id")
    private AuctionItem auctionItem;

    @Column(length = 10)
    private String bidding;

    @CreationTimestamp
    private LocalDateTime creDate;
}
