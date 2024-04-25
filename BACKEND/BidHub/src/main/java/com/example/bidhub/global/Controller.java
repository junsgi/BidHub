package com.example.bidhub.global;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class Controller {
    @GetMapping("/")
    public String getTest(){
        return "하잉";
    }
}
