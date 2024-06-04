package com.example.bidhub.auctionitem;

import com.example.bidhub.domain.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuctionItemRepository extends JpaRepository<AuctionItem, String> {
    @Query(value = "SELECT AUCTION_ITEM_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
    public Long getSeq();


    public Optional<AuctionItem> findById(String aitemId);


    @Query(value = "SELECT items.AITEM_ID, items.MEM_ID, items.AITEM_TITLE, items.AITEM_START, items.AITEM_IMMEDIATE, items.AITEM_IMG, items.AITEM_DATE, items.AITEM_CURRENT, items.AITEM_CONTENT, items.AITEM_BID, items.AITEM_STATUS " +
            "FROM ( SELECT AITEM_ID, MEM_ID, AITEM_TITLE, AITEM_START, AITEM_IMMEDIATE, AITEM_IMG, AITEM_DATE, AITEM_CURRENT, AITEM_CONTENT, AITEM_BID, AITEM_STATUS, " +
            "           ROW_NUMBER() OVER (ORDER BY AITEM_ID DESC) as rn " +
            "    FROM auction_item) items " +
            "WHERE items.rn BETWEEN :st AND :ed " +
            "ORDER BY items.AITEM_ID DESC", nativeQuery = true)
    public List<AuctionItem> findAllByStEd(@Param("st") Integer st, @Param("ed") Integer ed);

    @Query(value = "SELECT items.AITEM_ID, items.MEM_ID, items.AITEM_TITLE, items.AITEM_START, items.AITEM_IMMEDIATE, items.AITEM_IMG, items.AITEM_DATE, items.AITEM_CURRENT, items.AITEM_CONTENT, items.AITEM_BID, items.AITEM_STATUS " +
            "FROM ( SELECT AITEM_ID, MEM_ID, AITEM_TITLE, AITEM_START, AITEM_IMMEDIATE, AITEM_IMG, AITEM_DATE, AITEM_CURRENT, AITEM_CONTENT, AITEM_BID, AITEM_STATUS, " +
            "           ROW_NUMBER() OVER (ORDER BY AITEM_ID DESC) as rn " +
            "       FROM auction_item " +
            "       WHERE AITEM_DATE >= LOCALTIMESTAMP ) items " +
            "WHERE items.rn BETWEEN :st AND :ed " +
            "ORDER BY items.AITEM_ID DESC", nativeQuery = true)
    public List<AuctionItem> findAllByStEdProcessing(@Param("st") Integer st, @Param("ed") Integer ed);

    @Query(value = "SELECT COUNT(*) as count " +
            "FROM auction_item items " +
            "WHERE items.AITEM_DATE >= LOCALTIMESTAMP ", nativeQuery = true)
    public Integer findAllByStEdProcessingCount();

    @Query(value = "SELECT items.AITEM_ID, items.MEM_ID, items.AITEM_TITLE, items.AITEM_START, items.AITEM_IMMEDIATE, items.AITEM_IMG, items.AITEM_DATE, items.AITEM_CURRENT, items.AITEM_CONTENT, items.AITEM_BID, items.AITEM_STATUS " +
            "FROM ( SELECT AITEM_ID, MEM_ID, AITEM_TITLE, AITEM_START, AITEM_IMMEDIATE, AITEM_IMG, AITEM_DATE, AITEM_CURRENT, AITEM_CONTENT, AITEM_BID, AITEM_STATUS, " +
            "           ROW_NUMBER() OVER (ORDER BY AITEM_ID DESC) as rn " +
            "       FROM auction_item " +
            "       WHERE MEM_ID = :mine ) items " +
            "WHERE items.rn BETWEEN :st AND :ed " +
            "ORDER BY items.AITEM_ID DESC", nativeQuery = true)
    public List<AuctionItem> findAllByStEdMine(@Param("st") Integer st, @Param("ed") Integer ed, @Param("mine") String mine);

    @Query(value = "SELECT COUNT(*) as count " +
            "FROM auction_item items " +
            "WHERE items.MEM_ID = :mine", nativeQuery = true)
    public Integer findAllByStEdMineCount(@Param("mine") String mine);
}
