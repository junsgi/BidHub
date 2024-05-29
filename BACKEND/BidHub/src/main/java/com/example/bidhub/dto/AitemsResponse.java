package com.example.bidhub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AitemsResponse {
    String aitem_id;
    String title;
    Long current;
    String Immediate;
    String remaining;
    String status;
    @Builder
    public AitemsResponse(String aitem_id, String title, Long current, String immediate, String remaining, String status) {
        this.aitem_id = aitem_id;
        this.title = title;
        this.current = current;
        this.Immediate = immediate;
        this.remaining = remaining;
        this.status = status;
    }

}
