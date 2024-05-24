package com.example.bidhub.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BiddingRequest {
    private String userId;
    private String itemId;
    private String current;
}
