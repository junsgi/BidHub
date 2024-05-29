package com.example.bidhub.success;

import com.example.bidhub.dto.AitemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
