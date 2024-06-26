package com.capstone.closetconnect.controllers;

import com.capstone.closetconnect.dtos.ClothingItemsDto;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.services.ClothingItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clothingItems")
@Slf4j
public class ClothingItemsController {

    private final ClothingItemsService clothingItemsService;

    @GetMapping("/search")
    public ResponseEntity<List<ClothingItemsDto>> searchUserClothingItems
            (@RequestParam Long userId, @RequestParam(required = false) String itemName,
             @RequestParam(required = false) ClothType itemType, @RequestParam(required = false)
             Gender gender){
        log.info("Incoming search request for user with id {} with parameters: " +
                "itemName={}, itemType={}, gender={}", userId, itemName, itemType, gender);
        return new ResponseEntity<>(clothingItemsService.
                searchUserClothingItems(userId,itemName,itemType,gender),
                HttpStatus.OK);
    }

}
