package com.example.bidhub.jwt;

import com.example.bidhub.domain.Member;
import com.example.bidhub.domain.RefreshToken;
import com.example.bidhub.global.CreateAccessTokenRequest;
import com.example.bidhub.global.CreateAccessTokenResponse;
import com.example.bidhub.member.MemberRepository;
import com.example.bidhub.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final JwtProvider JwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    private final JwtProperties jwtProperties;
    public CreateAccessTokenResponse createAccessToken(CreateAccessTokenRequest request){
        // Create Access Token and refresh token by username and password
        if(request.getMemId()!=null && request.getMemPw()!= null) {
            Optional<Member> member = memberRepository.findById(request.getMemId());
            if(bcryptPasswordEncoder.matches(request.getMemPw(), member.get().getMemPw())) {
                return new CreateAccessTokenResponse(
                        JwtProvider.createToken(member.get(), Duration.ofMinutes(jwtProperties.getDuration())),
                        createRefreshToken(member.get()));
            }
        }
        // Create Access token by refresh token
        else if (request.getToken()!=null) {
            if(JwtProvider.isValidToken(request.getToken())) {
                String memId = refreshTokenService.findByRefreshToken(request.getToken()).getMemId();
                Optional<Member> user = memberRepository.findById(memId);
                return new CreateAccessTokenResponse(
                        JwtProvider.createToken(user.get(), Duration.ofMinutes(jwtProperties.getDuration())),
                        null);
            }
        }
        throw new IllegalArgumentException("Invalid password");
    }
    public String createRefreshToken(Member member) throws IllegalArgumentException{
        String token = JwtProvider.createToken(member, Duration.ofHours(jwtProperties.getRefreshDuration()));
        RefreshToken refreshToken = refreshTokenService.findByMemId(member.getMemId());
        refreshToken.setRefreshToken(token);
        refreshTokenService.save(refreshToken); // save refresh token
        return token;
    }
}