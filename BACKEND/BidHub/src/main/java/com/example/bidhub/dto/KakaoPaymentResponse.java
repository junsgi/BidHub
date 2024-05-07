package com.example.bidhub.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KakaoPaymentResponse {
    private final String tid;
    private final String next_redirect_app_url;
    private final String next_redirect_mobile_url;
    private final String next_redirect_pc_url;
    private final String android_app_scheme;
    private final String ios_app_scheme;
    private final LocalDateTime created_at;
}
