package com.example.bidhub.dto;

import com.example.bidhub.domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String id;
    private String nickname;
    private String pw;

    public static Member toMember(SignUpDTO dto) {
        return Member.builder()
                .memId(dto.getId())
                .memPw(dto.getPw())
                .memPoint(0L)
                .memNickname(dto.getNickname())
                .build();
    }
}
