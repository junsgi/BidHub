package com.example.bidhub.success;

import com.example.bidhub.auction.AuctionService;
import com.example.bidhub.auctionitem.AuctionItemRepository;
import com.example.bidhub.auctionitem.AuctionItemService;
import com.example.bidhub.domain.AuctionItem;
import com.example.bidhub.domain.Member;
import com.example.bidhub.domain.SucBidder;
import com.example.bidhub.dto.AitemsResponse;
import com.example.bidhub.dto.BiddingRequest;
import com.example.bidhub.dto.ResponseDTO;
import com.example.bidhub.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SucService {
    private final SucBidderRepository repository;
    private final AuctionItemService auctionItemService;
    private final AuctionService auctionService;
    private final MemberRepository memberRepository;
    private final AuctionItemRepository auctionItemRepository;
    public List<AitemsResponse> getMemberItems(String memId) {
        List<SucBidder> list = repository.findAllById_memId(memId);
        List<AitemsResponse> res = new LinkedList<>();
        for(SucBidder item : list) {
            res.add(AitemsResponse.builder()
                    .aitem_id(item.getSucBidderId().getAitemId().getAitemId())
                    .title(item.getSucBidderId().getAitemId().getAitemTitle())
                    .current(Long.parseLong(item.getSucBidderId().getAitemId().getAitemCurrent().trim()))
                    .immediate(item.getSucBidderId().getAitemId().getAitemImmediate())
                    .remaining(String.valueOf(auctionItemService.getRemaining(item.getSucBidderId().getAitemId().getAitemDate())))
                    .status(item.getSucBidderId().getAitemId().getAitemStatus())
                    .build()
            );
        }
        return res;
    }

    public ResponseDTO insertSucBidder(BiddingRequest request) {
        ResponseDTO res = ResponseDTO.builder().build();
        Optional<Member> mObj = memberRepository.findById(request.getUserId());
        Optional<AuctionItem> aObj = auctionItemRepository.findById(request.getItemId());

        if (mObj.isPresent() && aObj.isPresent()) {
            Member mem = mObj.get(); AuctionItem item = aObj.get();

            // 포인트 차감
            long point = mem.getMemPoint() - Long.parseLong(item.getAitemCurrent().trim());
            if (point < 0) return auctionService.biddingSwitch(1); // 포인트가 부족합니다.
            mem.setMemPoint(point);


            // item status, date update
            item.setAitemStatus("0");
            item.setAitemDate(LocalDateTime.of(1970, 1, 1, 0, 0));


            memberRepository.save(mem);
            auctionItemRepository.save(item);

            res.setStatus(true);
            res.setMessage("결제 완료");
        }else {
            res.setStatus(false);
            res.setMessage("다시 시도해주세요");
        }
        return res;
    }
}
