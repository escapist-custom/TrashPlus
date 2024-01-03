package com.samsung.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "link")
@Getter
@Setter
@NoArgsConstructor
public class UserProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "user_id")
    private Long userId;

    public UserProduct(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}