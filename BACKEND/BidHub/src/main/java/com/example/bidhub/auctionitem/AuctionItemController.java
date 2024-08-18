package com.example.bidhub.auctionitem;

import com.example.bidhub.dto.*;
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
    public AuctionListResponse getItems(
            @RequestParam(name = "st") Integer st,
            @RequestParam(name = "sort") Integer sort,
            @RequestParam(name = "id", required = false) String id
    ) {
        if (sort == 1 && (id == null || id.isEmpty())) sort = 0;
        return service.getItems(st * 9 - 8, st * 9, sort, id);
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

    @DeleteMapping(path = "/{id}")
    public ResponseDTO deleteItem(@PathVariable("id") String id) { return service.deleteItem(id); }
}
