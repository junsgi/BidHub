package com.example.bidhub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AuctionItem {
    @Id
    @Column(length = 50)
    private String aitemId;

    private LocalDateTime aitemDate;

    @Column(length = 10)
    private String aitemStart;

    @Column(length = 10)
    private String aitemImmediate;

    @Column(length = 10)
    private String aitemBid;

    @Column(length = 10)
    private String aitemCurrent;

    @Column(length = 300)
    private String aitemTitle;

    @Column(length = 300)
    private String aitemImg;

    @Column(length = 1500)
    private String aitemContent;

    @ManyToOne
    @JoinColumn(name = "memId")
    private Member member;


}
