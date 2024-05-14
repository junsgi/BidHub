package com.example.bidhub.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuctionItemRequest {
    private MultipartFile img;

    private String title;

    private String content;

    private String start;

    private String bid;

    private String date;

    private String immediate;

    private String userId;


}
