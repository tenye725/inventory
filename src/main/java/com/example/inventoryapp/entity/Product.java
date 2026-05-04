
package com.example.inventoryapp.entity;


// ─────────────────────────────────────────────────────────────
// entity/Product.java — JPA 엔티티
// ─────────────────────────────────────────────────────────────

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer stock;

    // 응답한 App 서버 IP — 로드밸런싱 확인용
    @Column(name = "server_ip", length = 50)
    private String serverIp;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product(String name, int stock, String serverIp) {
        this.name     = name;
        this.stock    = stock;
        this.serverIp = serverIp;
    }
}
