package com.capstone.closetconnect.services;

import com.capstone.closetconnect.dtos.ClothingItemsDto;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;

import java.util.List;

public interface ClothingItemsService {

    List<ClothingItemsDto> searchUserClothingItems
            (Long userId, String itemName, ClothType itemType, Gender gender);

}
