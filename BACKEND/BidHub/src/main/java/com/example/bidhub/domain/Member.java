package com.example.bidhub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
    @Id
    private String memId;

    private String memPw;
    private Long memPoint;

    @Column(length = 50)
    private String memNickname;

    @Column(length = 30)
    private String memEmail;

    public Member() {}

    @Builder
    public Member(String memId, String memPw, Long memPoint, String memNickname, String memEmail) {
        this.memId = memId;
        this.memPw = memPw;
        this.memPoint = memPoint;
        this.memNickname = memNickname;
        this.memEmail = memEmail;
    }

}
