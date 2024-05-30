package com.example.bidhub.success;

import com.example.bidhub.domain.SucBidder;
import com.example.bidhub.domain.SucBidderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SucBidderRepository extends JpaRepository<SucBidder, SucBidderId> {

    @Query(value = "SELECT aci.AITEM_ID, aci.AITEM_DATE, aci.AITEM_START, aci.AITEM_IMMEDIATE, aci.AITEM_BID, aci.AITEM_CURRENT, aci.AITEM_TITLE, aci.AITEM_IMG, aci.AITEM_CONTENT, aci.mem_id, aci.AITEM_STATUS " +
            "FROM auction_item aci JOIN auction ac " +
            "ON aci.aitem_id = ac.aitem_id " +
            "WHERE (aci.aitem_date <= SYSDATE OR aci.aitem_status = '1') " +
            "AND ac.mem_id = :memId " +
            "GROUP BY aci.AITEM_ID, aci.AITEM_DATE, aci.AITEM_START, aci.AITEM_IMMEDIATE, aci.AITEM_BID, aci.AITEM_CURRENT, aci.AITEM_TITLE, aci.AITEM_IMG, aci.AITEM_CONTENT, aci.mem_id, ac.mem_id, aci.AITEM_STATUS " +
            "UNION " +
            "SELECT aci.AITEM_ID, aci.AITEM_DATE, aci.AITEM_START, aci.AITEM_IMMEDIATE, aci.AITEM_BID, aci.AITEM_CURRENT, aci.AITEM_TITLE, aci.AITEM_IMG, aci.AITEM_CONTENT, aci.mem_id, aci.AITEM_STATUS " +
            "FROM suc_bidder sb JOIN auction_item aci " +
            "ON sb.aitem_id = aci.aitem_id " +
            "WHERE sb.mem_id = :memId " +
            "ORDER BY 11 desc, 1 desc", nativeQuery = true)
    public List<SucBidder> findAllById_memId(@Param("memId") String memId);
}
