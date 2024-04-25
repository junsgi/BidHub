package com.example.bidhub.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAccessTokenResponse {
    private String accessToken;
    private String refreshToken;
}
