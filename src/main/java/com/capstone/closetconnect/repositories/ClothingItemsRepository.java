package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.models.ClothingItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothingItemsRepository extends JpaRepository<ClothingItems, Long> {

    Optional<List<ClothingItems>> findByUserIdAndNameContaining(Long userId, String itemName);

    Optional<List<ClothingItems>> findByUserIdAndType(Long userId, ClothType itemType);

    Optional<List<ClothingItems>> findByUserIdAndGender(Long userId, Gender gender);

}
