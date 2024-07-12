package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.models.ClothingItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothingItemsRepository extends JpaRepository<ClothingItems, Long> {

    Page<ClothingItems> findByUserIdAndNameContaining(Long userId, String itemName, Pageable pageable);

    Page<ClothingItems> findByUserIdAndType(Long userId, ClothType itemType,Pageable pageable);

    Page<ClothingItems> findByUserIdAndGender(Long userId, Gender gender, Pageable pageable);

}
