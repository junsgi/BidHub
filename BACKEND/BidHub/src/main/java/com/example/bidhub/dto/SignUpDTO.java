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
    private String email;

    public static Member toMember(SignUpDTO dto) {
        return Member.builder()
                .memId(dto.getId())
                .memPw(dto.getPw())
                .memPoint(0L)
                .memEmail(dto.getEmail())
                .memNickname(dto.getNickname())
                .build();
    }
}
