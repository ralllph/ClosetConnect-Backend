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

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface ClothingItemsRepository extends JpaRepository<ClothingItems, Long> {

    Page<ClothingItems> findByUserIdAndNameContaining(Long userId, String itemName,
                                                      Pageable pageable);

    Page<ClothingItems> findByUserIdAndType(Long userId, ClothType itemType,Pageable pageable);

    Page<ClothingItems> findByUserIdAndGender(Long userId, Gender gender, Pageable pageable);

    @Query("SELECT new com.capstone.closetconnect.dtos.response.ClothDetailsWithUser(" +
            "ci.id, ci.name, ci.description, ci.photoUrl, ci.status, " +
            "ci.user.id, ci.user.name AS userFullName,ci.clothingItemSize," +
            " ci.createdAt,ci.gender,ci.type) " +
            "FROM ClothingItems ci JOIN ci.user WHERE ci.status= 'AVAILABLE'  " +
            "AND ci.name LIKE %:itemName% " +
            " ORDER BY ci.createdAt DESC")
    Page<ClothDetailsWithUser> getAllClothingItemsByNameContaining(@Param("itemName")
                                                                   String itemName,
                                                                   Pageable pageable);

    @Query("SELECT new com.capstone.closetconnect.dtos.response.ClothDetailsWithUser(" +
            "ci.id, ci.name, ci.description, ci.photoUrl,ci.status, " +
            "ci.user.id, ci.user.name AS userFullName,ci.clothingItemSize," +
            " ci.createdAt,ci.gender,ci.type) " +
            "FROM ClothingItems ci JOIN ci.user WHERE ci.status='AVAILABLE'" +
            "AND ci.gender =:gender " +
            " ORDER BY ci.createdAt DESC")
    Page<ClothDetailsWithUser> getAllClothingItemsByGender(@Param("gender") Gender gender,
                                                                   Pageable pageable);

    @Query("SELECT new com.capstone.closetconnect.dtos.response.ClothDetailsWithUser(" +
            "ci.id, ci.name, ci.description, ci.photoUrl,ci.status, " +
            "ci.user.id, ci.user.name AS userFullName,ci.clothingItemSize," +
            " ci.createdAt,ci.gender,ci.type) " +
            "FROM ClothingItems ci JOIN ci.user WHERE ci.status='AVAILABLE'" +
            "AND ci.type = :type " +
            " ORDER BY ci.createdAt DESC")
    Page<ClothDetailsWithUser> getAllClothingItemsByType(@Param("type") ClothType type,
                                                           Pageable pageable);

    @Query("SELECT new com.capstone.closetconnect.dtos.response.ClothDetailsWithUser(" +
            "ci.id, ci.name, ci.description, ci.photoUrl, ci.status, " +
            "ci.user.id, ci.user.name AS userFullName,ci.clothingItemSize," +
            " ci.createdAt,ci.gender,ci.type) " +
            "FROM ClothingItems ci JOIN ci.user WHERE ci.status='AVAILABLE' " +
            "ORDER BY ci.createdAt DESC")
    Page<ClothDetailsWithUser> getAllClothingItemsWithUserInfo(Pageable pageable);

    @Query("SELECT new com.capstone.closetconnect.dtos.response.ClothDetailsWithUser(" +
            "ci.id, ci.name, ci.description, ci.photoUrl, ci.status, " +
            "ci.user.id, ci.user.name AS userFullName,ci.clothingItemSize," +
            " ci.createdAt, ci.gender, ci.type) " +
            "FROM ClothingItems ci JOIN ci.user WHERE ci.status='AVAILABLE' " +
            "AND ci.createdAt >= :startDate " +
            "ORDER BY ci.createdAt DESC")
    Page<ClothDetailsWithUser> getRecentClothingItemsWithUserInfo
            (@Param("startDate") Date startDate,
             Pageable pageable);


    @Query("SELECT new com.capstone.closetconnect.dtos.response.ClothDetailsWithUser(" +
            "ci.id, ci.name, ci.description, ci.photoUrl, ci.status, " +
            "ci.user.id, ci.user.name AS userFullName,ci.clothingItemSize," +
            " ci.createdAt,ci.gender,ci.type) " +
            "FROM ClothingItems ci JOIN ci.user WHERE ci.id = :clothId " +
            "ORDER BY ci.createdAt DESC")
    Optional<ClothDetailsWithUser> getClothingItemWithUserDetails(@Param("clothId")
                                                                  Long clothId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ClothingItems ci WHERE ci.id = :clothId")
    void deleteClothingItem(@Param("clothId") Long clothId);
}