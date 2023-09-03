package com.samsung.repository;

import com.samsung.domain.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<UserProduct, Long> {

    @Query(value = "SELECT * FROM link WHERE user_id = :id", nativeQuery = true)
    List<Long> findByUserId(long id);
}
