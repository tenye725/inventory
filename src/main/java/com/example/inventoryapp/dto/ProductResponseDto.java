package com.example.inventoryapp.dto;


import com.example.inventoryapp.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponseDto {
    private Long          id;
    private String        name;
    private Integer       stock;
    private String        serverIp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponseDto from(Product p) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id        = p.getId();
        dto.name      = p.getName();
        dto.stock     = p.getStock();
        dto.serverIp  = p.getServerIp();
        dto.createdAt = p.getCreatedAt();
        dto.updatedAt = p.getUpdatedAt();
        return dto;
    }
}
