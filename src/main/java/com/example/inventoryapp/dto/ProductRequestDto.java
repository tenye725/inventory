package com.example.inventoryapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private String  name;
    private Integer stock;
}
