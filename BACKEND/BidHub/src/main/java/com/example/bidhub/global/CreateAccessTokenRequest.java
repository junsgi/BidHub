package com.example.bidhub.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccessTokenRequest {
    private String memId;
    private String memPw;
    private String token;
}
