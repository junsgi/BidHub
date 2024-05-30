package com.example.bidhub.success;

import com.example.bidhub.dto.AitemsResponse;
import com.example.bidhub.dto.BiddingRequest;
import com.example.bidhub.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suc")
@RequiredArgsConstructor
public class SucController {
    private final SucService service;
    @GetMapping(path = "/{memId}")
    public List<AitemsResponse> getMemberItems(@PathVariable("memId") String memId) {
        return service.getMemberItems(memId);
    }
    @PostMapping(path = "/payment")
    public ResponseDTO insertSucBidder(@RequestBody BiddingRequest request) {
        return service.insertSucBidder(request);
    }
}
