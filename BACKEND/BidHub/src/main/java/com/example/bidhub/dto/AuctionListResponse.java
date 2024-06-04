package com.example.bidhub.dto;

import com.example.bidhub.domain.AuctionItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionListResponse {
    private List<AitemsResponse> list;
    private Integer len;
}
