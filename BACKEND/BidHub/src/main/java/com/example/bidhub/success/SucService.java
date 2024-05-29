package com.example.bidhub.success;

import com.example.bidhub.auctionitem.AuctionItemService;
import com.example.bidhub.domain.AuctionItem;
import com.example.bidhub.domain.SucBidder;
import com.example.bidhub.dto.AitemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SucService {
    private final SucBidderRepository repository;
    private final AuctionItemService auctionItemService;
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
}
