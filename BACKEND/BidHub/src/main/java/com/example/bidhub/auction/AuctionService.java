package com.example.bidhub.auction;

import com.example.bidhub.auctionitem.AuctionItemRepository;
import com.example.bidhub.domain.*;
import com.example.bidhub.dto.BiddingRequest;
import com.example.bidhub.dto.ResponseDTO;
import com.example.bidhub.dto.SucBidderDTO;
import com.example.bidhub.member.MemberRepository;
import com.example.bidhub.success.SucBidderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository repository;
    private final AuctionItemRepository auctionItemRepository;
    private final MemberRepository memberRepository;
    private final SucBidderRepository sucBidderRepository;
    public ResponseDTO bidding(BiddingRequest request, boolean imm) {
        ResponseDTO res = new ResponseDTO();
        Auction auction = new Auction();
        Optional<AuctionItem> obj = auctionItemRepository.findById(request.getItemId());
        Optional<Member> obj1 = memberRepository.findById(request.getUserId());

        if (obj.isPresent() && obj1.isPresent() && request.getCurrent().equals(obj.get().getAitemCurrent().trim())) {
            AuctionItem item = obj.get();
            Member mem = obj1.get();

            if (item.getAitemStatus().equals("2")) return biddingSwitch(5);

            long mem_point = mem.getMemPoint();

            long bid = Long.parseLong(item.getAitemBid());
            long current = Long.parseLong(item.getAitemCurrent().trim());
            long immediate = Long.parseLong(item.getAitemImmediate());
            long NEW = current + bid;

            long now = Duration.between(LocalDateTime.now(), item.getAitemDate()).getSeconds();
            if (mem_point < NEW) return biddingSwitch(1);
            if (now < 0) return biddingSwitch(2);

            if (imm) { // 즉시 구매
                if (immediate < current) return biddingSwitch(3);
                if (mem_point < immediate) return biddingSwitch(1);

                // Auction Item
                item.setAitemCurrent(String.valueOf(immediate));
                item.setAitemDate(LocalDateTime.of(1970, 1, 1, 0, 0));
                item.setAitemCurrent(String.valueOf(immediate));
                item.setAitemStatus("0");

                //member
                mem.setMemPoint(mem_point - immediate);

                // SucBidder
                SucBidderId sbId = new SucBidderId();
                sbId.setAitemId(auctionItemRepository.findById(item.getAitemId()).get());
                sbId.setMemId(memberRepository.findById(mem.getMemId()).get());
                SucBidder sucBidder = new SucBidder(sbId);

                auctionItemRepository.save(item);
                memberRepository.save(mem);
                sucBidderRepository.save(sucBidder);
                return biddingSwitch(4);
            }else { // 입찰
                item.setAitemCurrent(String.valueOf(NEW));
                auction.setMember(mem);
                auction.setAuctionItem(item);
                auction.setBidding(String.valueOf(NEW));

                auctionItemRepository.save(item);
                repository.save(auction);
                return biddingSwitch(42);
            }
        }else {
            res.setStatus(false);
            res.setMessage("다시 시도해주세요");
        }
        return res;
    }

    public ResponseDTO biddingClose(BiddingRequest request) {
        Optional<SucBidderDTO> obj = repository.findByAitemId(request.getItemId());
        Optional<AuctionItem> aitem = auctionItemRepository.findById(request.getItemId());
        ResponseDTO res = new ResponseDTO();
        if (obj.isPresent()) {
            SucBidderDTO dto = obj.get();
            res.setMessage(String.format("중단하시겠습니까?\n낙찰자 : %s, 금액 : %s", dto.getMemId(), dto.getBidding()));
        }else
            res.setMessage("취소하시겠습니까?\n낙찰자 없음");

        if (aitem.isPresent()) {
            AuctionItem item = aitem.get();
            item.setAitemStatus("2");
            auctionItemRepository.save(item);
        }
        return res;
    }

    public ResponseDTO decide(BiddingRequest request) {
        ResponseDTO res = ResponseDTO.builder().build();
        AuctionItem item = auctionItemRepository.findById(request.getItemId()).get();
        Optional<SucBidderDTO> obj = repository.findByAitemId(request.getItemId());
        Member mem = memberRepository.findById(request.getUserId()).get();
        if (request.getFlag()) { // 취소한다면
            item.setAitemDate(LocalDateTime.of(1970, 1, 1, 0, 0));
            item.setAitemStatus("0");

            if (obj.isPresent()) { // 낙찰
                SucBidderId id = new SucBidderId();
                id.setMemId(mem);
                id.setAitemId(item);
                SucBidder entity = new SucBidder();
                entity.setSucBidderId(id);
                sucBidderRepository.save(entity);
            }
            res.setStatus(true);
            res.setMessage("경매가 종료되었습니다.");
        }else {
            item.setAitemStatus("1");
        }

        auctionItemRepository.save(item);
        return res;
    }

    private ResponseDTO biddingSwitch(int i) {
        return switch (i) {
            case 1 -> ResponseDTO.builder().status(false).message("포인트가 부족합니다.").build();
            case 2 -> ResponseDTO.builder().status(false).message("경매가 종료되었습니다.").build();
            case 3 -> ResponseDTO.builder().status(false).message("즉시 구매를 할 수 없습니다.").build();
            case 4 -> ResponseDTO.builder().status(true).message("즉시 구매 완료.").build();
            case 5 -> ResponseDTO.builder().status(false).message("경매가 중지되었습니다. 다시 시도해주세요.").build();
            default -> ResponseDTO.builder().status(true).message("입찰 완료").build();
        };
    }
}
