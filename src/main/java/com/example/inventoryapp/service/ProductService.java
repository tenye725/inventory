package com.example.inventoryapp.service;


import com.example.inventoryapp.dto.ProductRequestDto;
import com.example.inventoryapp.dto.ProductResponseDto;
import com.example.inventoryapp.entity.Product;
import com.example.inventoryapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private String getServerIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id));
        return ProductResponseDto.from(p);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findLowStock(int threshold) {
        return productRepository.findByStockLessThanEqual(threshold).stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto create(ProductRequestDto dto) {
        Product p = new Product(dto.getName(), dto.getStock(), getServerIp());
        return ProductResponseDto.from(productRepository.save(p));
    }

    @Transactional
    public ProductResponseDto updateStock(Long id, int quantity) {
        Product p = productRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id));
        p.setStock(quantity);
        p.setServerIp(getServerIp());
        return ProductResponseDto.from(p);
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id);
        }
        productRepository.deleteById(id);
    }
}
