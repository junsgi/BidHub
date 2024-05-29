package com.example.bidhub.auctionitem;

import com.example.bidhub.dto.AuctionItemRequest;
import com.example.bidhub.dto.AuctionItemResponse;
import com.example.bidhub.dto.AitemsResponse;
import com.example.bidhub.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auctionitem")
public class AuctionItemController {
    private final AuctionItemService service;
    @PostMapping(path = "/submit")
    public ResponseDTO submit(AuctionItemRequest request) {
        return service.submit(request);
    }

    @GetMapping(path = "/")
    public List<AitemsResponse> getItems(@RequestParam(name = "st", required = false) Integer st) {
        if (st != null) return service.getItems(st * 5 - 4, st * 5);
        else return service.getItems();
    }

    @GetMapping(path = "/count")
    public Integer getCount() { return service.getCount(); }


    @GetMapping(path = "/{aitemId}")
    public AuctionItemResponse getItem(@PathVariable("aitemId") String aitemId){
        return service.getItem(aitemId);
    }

    @GetMapping(path = "/img/{aitemId}")
    public byte[] getImg(@PathVariable("aitemId") String aitemId){
        return service.getImg(aitemId);
    }

}
