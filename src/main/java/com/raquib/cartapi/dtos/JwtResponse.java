package com.raquib.cartapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class JwtResponse {
    private String jwtToken;
}
