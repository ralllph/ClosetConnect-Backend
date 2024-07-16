package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.dtos.response.ClothDetailsWithUser;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.models.ClothingItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface ClothingItemsRepository extends JpaRepository<ClothingItems, Long> {

    Page<ClothingItems> findByUserIdAndNameContaining(Long userId, String itemName, Pageable pageable);

    Page<ClothingItems> findByUserIdAndType(Long userId, ClothType itemType,Pageable pageable);

    Page<ClothingItems> findByUserIdAndGender(Long userId, Gender gender, Pageable pageable);

    @Query("SELECT new com.capstone.closetconnect.dtos.response.ClothDetailsWithUser(" +
            "ci.id, ci.name, ci.description, ci.photoUrl, ci.status, " +
            "ci.user.id, ci.user.name AS userFullName, ci.createdAt) " +
            "FROM ClothingItems ci JOIN ci.user ORDER BY ci.createdAt DESC")
    Page<ClothDetailsWithUser> getAllClothingItemsWithUserInfo(Pageable pageable);

    @Query("SELECT new com.capstone.closetconnect.dtos.response.ClothDetailsWithUser(" +
            "ci.id, ci.name, ci.description, ci.photoUrl, ci.status, " +
            "ci.user.id, ci.user.name AS userFullName, ci.createdAt) " +
            "FROM ClothingItems ci JOIN ci.user WHERE ci.id = :clothId ORDER BY ci.createdAt DESC")
    Optional<ClothDetailsWithUser> getClothingItemWithUserDetails(@Param("clothId") Long clothId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ClothingItems ci WHERE ci.id = :clothId")
    void deleteClothingItem(@Param("clothId") Long clothId);
}