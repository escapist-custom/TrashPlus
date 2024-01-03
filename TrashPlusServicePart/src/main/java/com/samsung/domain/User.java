package com.samsung.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Builder
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long userId;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "control_sum")
    private Integer controlSum;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "link", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"))
    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void cleanProducts() {
        this.products.clear();
    }
}
