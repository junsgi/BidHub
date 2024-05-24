package com.example.bidhub.auction;

import com.example.bidhub.dto.BiddingRequest;
import com.example.bidhub.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService service;

    @PostMapping(path = "/bidding")
    public ResponseDTO bidding(@RequestBody BiddingRequest request) {
        return service.bidding(request, false);
    }

    @PostMapping(path = "/bidding/immediately")
    public ResponseDTO biddingImm(@RequestBody BiddingRequest request) {
        return service.bidding(request, true);
    }

}
