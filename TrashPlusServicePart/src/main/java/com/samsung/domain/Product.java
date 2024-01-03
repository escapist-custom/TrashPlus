package com.samsung.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true)
    private Long productId;

    @Column(name = "name")
    private String nameOfProduct;

    @Column(name = "product_code", unique = true)
    private Long productCode;

    @Column(name = "information", nullable = false, columnDefinition = "TEXT")
    private String information;

    @Column(name = "link_photo")
    private String linkPhoto;

    @Column(name = "class_cover")
    private String classOfCover;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JsonIgnore
    private Set<User> users = new HashSet<>();

}