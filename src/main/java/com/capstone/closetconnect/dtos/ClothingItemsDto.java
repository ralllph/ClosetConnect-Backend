package com.capstone.closetconnect.dtos;

import com.capstone.closetconnect.models.ClothingItems;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothingItemsDto {

    private String photoUrl;

    private String description;

    private String type;

    private String itemCondition;

    private String clothingItemSize;

    private String status;

    private String source;

    public static ClothingItems toClothingItemEntity(ClothingItemsDto clothingItemsDto){
        ClothingItems clothingItem = new ClothingItems();
        clothingItem.setPhotoUrl(clothingItemsDto.getPhotoUrl());
        clothingItem.setDescriptiom(clothingItemsDto.getDescription());
        clothingItem.setType(clothingItemsDto.getType());
        clothingItem.setItemCondition(clothingItemsDto.getItemCondition());
        clothingItem.setClothingItemSize(clothingItemsDto.getClothingItemSize());
        clothingItem.setStatus(clothingItemsDto.getStatus());
        clothingItem.setSource(clothingItemsDto.getSource());
        return clothingItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothingItemsDto that = (ClothingItemsDto) o;
        return Objects.equals(photoUrl, that.photoUrl) && Objects.equals(description, that.description) && Objects.equals(type, that.type) && Objects.equals(itemCondition, that.itemCondition) && Objects.equals(clothingItemSize, that.clothingItemSize) && Objects.equals(status, that.status) && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoUrl, description, type, itemCondition, clothingItemSize, status, source);
    }

    @Override
    public String toString() {
        return "ClothingItemsDto{" +
                "photoUrl='" + photoUrl + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", itemCondition='" + itemCondition + '\'' +
                ", clothingItemSize='" + clothingItemSize + '\'' +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
