package com.example.bidhub.dto;

import lombok.*;

@Setter
@Getter
public class UserInfo {
    private String nickname;
    private Long point;

    @Builder
    public UserInfo(String nickname, Long point) {
        this.nickname = nickname;
        this.point = point;
    }
}
