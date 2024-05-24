package com.example.bidhub.success;

import com.example.bidhub.domain.SucBidder;
import com.example.bidhub.domain.SucBidderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucBidderRepository extends JpaRepository<SucBidder, SucBidderId> {
}
