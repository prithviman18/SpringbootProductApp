package com.productApp.FirstProductApp.repository;

import com.productApp.FirstProductApp.entity.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishList,Long> {
    // Find all wishlist items for a specific user
    List<WishList> findByUserId(Long userId);

    // Check if a specific product is in the user's wishlist
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    @Modifying
    @Transactional @Query("DELETE FROM WishList w WHERE w.user.id = :userId AND w.product.id = :productId")
    int deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

}
