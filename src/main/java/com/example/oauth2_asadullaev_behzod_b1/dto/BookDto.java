package com.example.oauth2_asadullaev_behzod_b1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String name;
    private double price;
    private String author;
    private int publishedYear;

}