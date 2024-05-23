package com.example.bidhub.auction;

import com.example.bidhub.auctionitem.AuctionItemRepository;
import com.example.bidhub.domain.Auction;
import com.example.bidhub.domain.AuctionId;
import com.example.bidhub.domain.AuctionItem;
import com.example.bidhub.domain.Member;
import com.example.bidhub.dto.BiddingRequest;
import com.example.bidhub.global.ResponseDTO;
import com.example.bidhub.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository repository;
    private final AuctionItemRepository auctionItemRepository;
    private final MemberRepository memberRepository;
    public ResponseDTO bidding(BiddingRequest request) {
        ResponseDTO res = new ResponseDTO();
        AuctionId id = AuctionId.builder()
                .memId(request.getUserId())
                .aitemId(request.getItemId())
                .build();
        Auction auction = new Auction();
        Optional<AuctionItem> obj = auctionItemRepository.findById(id.getAitemId());
        Optional<Member> obj1 = memberRepository.findById(id.getMemId());
        auction.setActionId(id);
        if (obj.isPresent() && obj1.isPresent()) {
            AuctionItem item = obj.get();

            long bid = Long.parseLong(item.getAitemBid());
            long current = Long.parseLong(item.getAitemCurrent().trim());
            long NEW = current + bid;
        }

        return res;
    }
}
