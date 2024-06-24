package com.capstone.closetconnect.services;

import com.capstone.closetconnect.dtos.ClothingItemsDto;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.exceptions.MissingParameterException;
import com.capstone.closetconnect.exceptions.NotFoundException;
import com.capstone.closetconnect.models.ClothingItems;
import com.capstone.closetconnect.repositories.ClothingItemsRepository;
import com.capstone.closetconnect.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ClothingItemsServiceImpl implements  ClothingItemsService{

    private final UserRepository userRepository;
    private final ClothingItemsRepository clothingItemsRepository;

    @Override
    public List<ClothingItemsDto> searchUserClothingItems
            (Long userId, String itemName, ClothType itemType, Gender gender) {
        if(itemName == null  && itemType ==null && gender==null)
            throw new MissingParameterException();
        userRepository.findById(userId).orElseThrow(()->new NotFoundException(itemName,userId));
        List<ClothingItemsDto> clothingItemsFound = new ArrayList<>();
        if(itemName!=null && !itemName.isEmpty()) {
            List<ClothingItems> foundClothingItems = clothingItemsRepository
                    .findByUserIdAndNameContaining(userId, itemName)
                    .orElse(Collections.emptyList());
            clothingItemsFound =  clothingListToDto(foundClothingItems);
        }
        else if(itemType!=null ) {
            List<ClothingItems> foundClothingItems = clothingItemsRepository
                    .findByUserIdAndType(userId, itemType)
                    .orElse(Collections.emptyList());
            clothingItemsFound =  clothingListToDto(foundClothingItems);
        }
        else if(gender!=null ) {
            List<ClothingItems> foundClothingItems = clothingItemsRepository
                    .findByUserIdAndGender(userId, gender)
                    .orElse(Collections.emptyList());
            clothingItemsFound =  clothingListToDto(foundClothingItems);
        }
        return clothingItemsFound;
    }

    private static  List<ClothingItemsDto>  clothingListToDto(List<ClothingItems> clothingItems){
        return clothingItems
                .stream()
                .map(clothingItem -> clothingItem.toClothingItemDto(clothingItem))
                .toList();
    }

}
