package com.example.bidhub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SucBidder {
    @EmbeddedId
    private SucBidderId sucBidderId;

    @CreationTimestamp
    @Column(name = "cre_date")
    private LocalDateTime creDate;

}
