package com.example.bidhub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
@Embeddable
@Data
public class SucBidderId implements Serializable {
    @Column(length = 50)
    private String aitemId;
    @Column(length = 50)
    private String memId;
}
