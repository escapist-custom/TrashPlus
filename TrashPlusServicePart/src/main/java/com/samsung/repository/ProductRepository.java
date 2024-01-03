package com.samsung.repository;

import com.samsung.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM products WHERE product_code = :code", nativeQuery = true)
    Product findByProductCode(@Param("code") long code);
    Product findById(long id);
    Product save(Product product);
    List<Product> findAll();
}
