package com.example.bidhub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuctionItemResponse {
    private String aitemId;
    private String aitemImg;
    private String aitemTitle;
    private String aitemContent;
    private String aitemStart;
    private String aitemBid;
    private LocalDateTime aitemDate;
    private String aitemImmediate;
    private String aitemCurrent;
    private String memId;
    private String status;
    @Builder
    public AuctionItemResponse(String aitemId, String aitemImg, String aitemTitle, String aitemContent, String aitemStart, String aitemBid, LocalDateTime aitemDate, String aitemImmediate, String aitemCurrent, String memId, String status) {
        this.aitemId = aitemId;
        this.aitemImg = aitemImg;
        this.aitemTitle = aitemTitle;
        this.aitemContent = aitemContent;
        this.aitemStart = aitemStart;
        this.aitemBid = aitemBid;
        this.aitemDate = aitemDate;
        this.aitemImmediate = aitemImmediate;
        this.aitemCurrent = aitemCurrent;
        this.memId = memId;
        this.status = status;
    }
}
