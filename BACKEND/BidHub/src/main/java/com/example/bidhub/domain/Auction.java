package com.example.bidhub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Auction {
    @EmbeddedId
    private AuctionId actionId;

    @Column(length = 10)
    private String bidding;

    @CreationTimestamp
    private LocalDateTime creDate;
}
