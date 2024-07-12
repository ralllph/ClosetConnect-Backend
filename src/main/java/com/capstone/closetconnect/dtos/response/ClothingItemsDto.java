package com.capstone.closetconnect.dtos.response;

import com.capstone.closetconnect.enums.ClothSize;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
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

    private Long id;

    private String photoUrl;

    private String description;

    private ClothType  type;

    private String itemCondition;

    private ClothSize clothingItemSize;

    private String status;

    private String source;

    private String name;

    private Gender gender;

    public static ClothingItems toClothingItemEntity(ClothingItemsDto clothingItemsDto){
        ClothingItems clothingItem = new ClothingItems();
        clothingItem.setPhotoUrl(clothingItemsDto.getPhotoUrl());
        clothingItem.setName(clothingItemsDto.getName());
        clothingItem.setGender(clothingItemsDto.getGender());
        clothingItem.setDescription(clothingItemsDto.getDescription());
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
        return Objects.equals(photoUrl, that.photoUrl) && Objects.equals(description, that.description) && Objects.equals(type, that.type) && Objects.equals(itemCondition, that.itemCondition) && clothingItemSize == that.clothingItemSize && Objects.equals(status, that.status) && Objects.equals(source, that.source) && Objects.equals(name, that.name) && gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoUrl, description, type, itemCondition, clothingItemSize, status, source, name, gender);
    }

    @Override
    public String toString() {
        return "ClothingItemsDto{" +
                "photoUrl='" + photoUrl + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", itemCondition='" + itemCondition + '\'' +
                ", clothingItemSize=" + clothingItemSize +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }

}
