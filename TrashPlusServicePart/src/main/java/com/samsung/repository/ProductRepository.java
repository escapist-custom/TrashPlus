package com.samsung.repository;

import com.samsung.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM products", nativeQuery = true)
    List<Product> findAllMy();
    Product findByProductCode(long code);

    Product save(Product product);
}
