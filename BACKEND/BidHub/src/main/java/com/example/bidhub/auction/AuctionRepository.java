package com.example.bidhub.auction;

import com.example.bidhub.domain.Auction;
import com.example.bidhub.dto.SucBidderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @Query(value = "SELECT a.mem_id as memId, a.bidding as bidding " +
            "FROM auction a " +
            "WHERE a.aitem_id = :aitemId " +
            "AND a.bidding = (SELECT MAX(a2.bidding) FROM auction a2 WHERE a2.aitem_id = :aitemId)", nativeQuery = true)
    public Optional<SucBidderDTO> findByAitemId(@Param("aitemId") String aitemId);
}
