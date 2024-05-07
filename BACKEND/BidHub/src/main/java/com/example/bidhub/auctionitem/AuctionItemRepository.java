package com.example.bidhub.auctionitem;

import com.example.bidhub.domain.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuctionItemRepository extends JpaRepository<AuctionItem, String> {
    @Query(value = "SELECT AUCTION_ITEM_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
    public Long getSeq();

    public Optional<AuctionItem> findById(String aitemId);
}
