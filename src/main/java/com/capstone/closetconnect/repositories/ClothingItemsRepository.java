package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.models.ClothingItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingItemsRepository extends JpaRepository<ClothingItems, Long> {
}
