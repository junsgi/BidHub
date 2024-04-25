package com.example.bidhub.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDTO {
    private String id;
    private String before;
    private String after;

    public UpdateDTO(String b, String a) {
        this.id = null;
        this.before = b;
        this.after = a;
    }
    public UpdateDTO(String id, String b, String a) {
        this.id = id;
        this.before = b;
        this.after = a;
    }
}
