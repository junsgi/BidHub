package com.example.bidhub.dto;


import lombok.*;

@Getter
@Setter
public class ResponseDTO{
    private boolean status;
    private String message;
    private String nickname;
    private Long point;

    public ResponseDTO() {}
    @Builder
    public ResponseDTO(boolean status, String message, String nickname, Long point) {
        this.status = status;
        this.message = message;
        this.nickname = nickname;
        this.point = point;
    }
}
