package com.samsung.repository;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<User, Long> {
    @Query(value = "INSERT INTO link (user_id, product_id) VALUES (:userId, :productId)", nativeQuery = true)
    void addProduct(long userId, long productId);

    @Query(value = "SELECT FROM link WHERE product_id = :productId", nativeQuery = true)
    List<Product> findByProductId(long productId);
}
