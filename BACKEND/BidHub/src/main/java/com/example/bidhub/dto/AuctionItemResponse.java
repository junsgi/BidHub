package com.example.bidhub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuctionItemResponse {
    private String aitemId;
    private String aitemTitle;
    private String aitemContent;
    private String aitemStart;
    private String aitemBid;
    private Long aitemDate;
    private String aitemImmediate;
    private Long aitemCurrent;
    private String memId;
    private String status;
    @Builder
    public AuctionItemResponse(String aitemId, String aitemTitle, String aitemContent, String aitemStart, String aitemBid, Long aitemDate, String aitemImmediate, Long aitemCurrent, String memId, String status) {
        this.aitemId = aitemId;
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
