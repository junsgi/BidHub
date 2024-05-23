package com.example.bidhub.auction;

import com.example.bidhub.domain.Auction;
import com.example.bidhub.domain.AuctionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, AuctionId> {
}
