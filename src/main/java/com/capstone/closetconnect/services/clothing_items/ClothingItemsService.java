package com.capstone.closetconnect.services.clothing_items;

import com.capstone.closetconnect.dtos.request.ClothingItem;
import com.capstone.closetconnect.dtos.response.ClothingItemsDto;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClothingItemsService {

    Page<ClothingItemsDto> searchUserClothingItems
            (Long userId, String itemName, ClothType itemType, Gender gender, Pageable pageable);

    void uploadClothingItemImage(Long clothId, MultipartFile file);

    byte[] getUserClothingImage(Long clothId);

    void  updateClothingPhotoUrl(String clothPhotoUrl, Long clothId);

    ClothingItemsDto createNewClothingItem(Long userId, ClothingItem newClothingItem);

    ClothingItemsDto updateClothingItem(Long clothId, Long userId, ClothingItem clothingItem);

    Page<ClothingItemsDto> getAllUserClothingItems(Long userId, Pageable pageable);

}
