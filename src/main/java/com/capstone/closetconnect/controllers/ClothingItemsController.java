package com.capstone.closetconnect.controllers;

import com.capstone.closetconnect.dtos.request.ClothingItem;
import com.capstone.closetconnect.dtos.response.ClothingItemsDto;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.exceptions.PaginationException;
import com.capstone.closetconnect.services.clothing_items.ClothingItemsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clothingItems")
@Slf4j
public class ClothingItemsController {

    private final ClothingItemsService clothingItemsService;

    @GetMapping("/search")
    public ResponseEntity<Page<ClothingItemsDto>> searchUserClothingItems
            (@RequestParam Long userId,
             @RequestParam(required = false) String itemName,
             @RequestParam(required = false) ClothType itemType,
             @RequestParam(required = false)
             Gender gender,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size
             ){
        log.info("Incoming search request for user with id {} with parameters: " +
                "itemName={}, itemType={}, gender={}", userId, itemName, itemType, gender);
        if(page<0 || size <1)
            throw new PaginationException();
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(clothingItemsService.
                searchUserClothingItems(userId,itemName,itemType,gender,pageable),
                HttpStatus.OK);
    }

    @PostMapping(value = "/{clothId}/cloth-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public HttpStatus uploadClothingItemImage(
            @PathVariable("clothId") Long clothId,
            @RequestParam("file")MultipartFile file
            ){
        clothingItemsService.uploadClothingItemImage(clothId, file);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/{clothId}/cloth-image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getUserClothingImage(@PathVariable("clothId") Long clothId){
        return new ResponseEntity<>(clothingItemsService.getUserClothingImage(clothId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ClothingItemsDto>> getUserClothingItems
            (@PathVariable("userId") Long userId,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size
            ){
        if(page<0 || size <1)
            throw new PaginationException();
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(clothingItemsService.getAllUserClothingItems(userId,pageable)
                , HttpStatus.OK);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<ClothingItemsDto> createClothingItem(
            @RequestBody @Valid ClothingItem clothingItem,
            @PathVariable("userId") Long userId){
        return new ResponseEntity<>(clothingItemsService.createNewClothingItem(userId,clothingItem),
                HttpStatus.OK);
    }

    @PutMapping("/update/{clothId}/{userId}")
    public ResponseEntity<ClothingItemsDto> updateClothingItem(
            @RequestBody @Valid ClothingItem clothingItem,
            @PathVariable("userId") Long userId,
            @PathVariable("clothId") Long clothId
            ){
        return new ResponseEntity<>(clothingItemsService.
                updateClothingItem(clothId,userId,clothingItem),
                HttpStatus.OK);
    }
}
