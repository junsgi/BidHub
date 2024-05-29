package com.example.bidhub.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BiddingRequest {
    private String userId;
    private String itemId;
    private String current;
    private Boolean flag;
}
