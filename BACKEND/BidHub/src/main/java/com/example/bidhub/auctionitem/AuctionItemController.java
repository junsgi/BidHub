package com.example.bidhub.auctionitem;

import com.example.bidhub.dto.AuctionItemRequest;
import com.example.bidhub.dto.AuctionItemResponse;
import com.example.bidhub.global.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auction")
public class AuctionItemController {
    private final AuctionItemService service;
    @PostMapping(path = "/submit")
    public ResponseDTO submit(@RequestBody AuctionItemRequest request) {
        return service.submit(request);
    }

    @GetMapping(path = "/{aitemId}")
    public AuctionItemResponse getItem(@PathVariable("aitemId") String aitemId){
        return service.getItem(aitemId);
    }
}
