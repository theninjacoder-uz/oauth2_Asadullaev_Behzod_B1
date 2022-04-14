package com.example.oauth2_asadullaev_behzod_b1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionResponse {
    private int statusCode;
    private String message;
}
