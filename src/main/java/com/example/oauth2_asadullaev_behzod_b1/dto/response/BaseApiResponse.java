package com.example.oauth2_asadullaev_behzod_b1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseApiResponse {
    private int status;
    private String message;
    private Object data;
}
