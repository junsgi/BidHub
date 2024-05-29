package com.example.bidhub.success;

import com.example.bidhub.domain.SucBidder;
import com.example.bidhub.domain.SucBidderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SucBidderRepository extends JpaRepository<SucBidder, SucBidderId> {

    @Query(value = "SELECT * FROM suc_bidder WHERE mem_id = :aitemId ORDER BY aitem_id desc ", nativeQuery = true)
    public List<SucBidder> findAllById_memId(@Param("aitemId") String aitemId);
}
