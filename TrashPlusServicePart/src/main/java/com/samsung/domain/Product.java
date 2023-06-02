package com.samsung.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String nameOfProduct;

    @Column(name = "product_code", unique = true)
    private long productCode;

    @Column(name = "information")
    private String information;

    @Column(name = "link_photo")
    private String linkPhoto;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<User> users;
}
