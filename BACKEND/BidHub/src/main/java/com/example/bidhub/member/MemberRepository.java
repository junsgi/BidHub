package com.example.bidhub.member;

import com.example.bidhub.domain.Member;
import com.example.bidhub.dto.MyItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {


    @Query(value = "select sb.sb_id as sb_id, ai.aitem_id as aitem_id, ai.aitem_current as price, ai.aitem_date as bid_date, ai.aitem_img as imgs, ai.aitem_title as title, ai.aitem_content as content " +
            "from suc_bidder as sb JOIN auction_item as ai " +
            "on sb.aitem_id = ai.aitem_id " +
            "where sb.mem_id = :memId", nativeQuery = true)
    List<MyItemDTO> findBySucMemId(@Param("memId") String memId);

}
