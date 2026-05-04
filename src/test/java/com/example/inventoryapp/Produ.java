package com.example.inventoryapp;

import com.example.inventoryapp.dto.ProductRequestDto;
import com.example.inventoryapp.dto.ProductResponseDto;
import com.example.inventoryapp.repository.ProductRepository;
import com.example.inventoryapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductServiceTest {

    @Autowired private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        ProductRequestDto dto1 = new ProductRequestDto();
        dto1.setName("AWS Cloud Book"); dto1.setStock(100);
        productService.create(dto1);
        ProductRequestDto dto2 = new ProductRequestDto();
        dto2.setName("Docker Handbook"); dto2.setStock(5);
        productService.create(dto2);
    }

    @Test
    @DisplayName("전체 조회 — 2건 반환")
    void findAll_shouldReturn2Products() {
        assertThat(productService.findAll()).hasSize(2);
    }

    @Test @DisplayName("재고 부족 조회 — stock <= 10")
    void findLowStock_shouldReturn1Product() {
        assertThat(productService.findLowStock(10))
                .hasSize(1)
                .extracting("name").containsExactly("Docker Handbook");
    }

    @Test @DisplayName("생성 — serverIp 자동 주입")
    void create_shouldSetServerIp() {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Kubernetes Guide"); dto.setStock(80);
        ProductResponseDto result = productService.create(dto);
        assertThat(result.getServerIp()).isNotBlank();
    }

    @Test @DisplayName("삭제 후 조회 — 404 예외")
    void delete_thenFindById_shouldThrow() {
        Long id = productService.findAll().get(0).getId();
        productService.delete(id);
        assertThatThrownBy(() -> productService.findById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
