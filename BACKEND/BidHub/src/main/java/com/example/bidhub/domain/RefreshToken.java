package com.example.bidhub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "REFRESH_ID_GENERATOR",
        sequenceName = "REFRESH_ID_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class RefreshToken {
    @Id
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator="REFRESH_ID_GENERATOR"
    )
    private Long id;
    @Column(nullable=false, unique=true)
    private String memId;
    @Column(nullable=false)
    private String refreshToken;
}