package com.capstone.closetconnect.dtos.request;

import com.capstone.closetconnect.enums.ClothSize;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.models.ClothingItems;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothingItem {

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Cloth type cannot be blank")
    private ClothType type;

    private String itemCondition;

    @NotNull(message = "Cloth size cannot be blank")
    private ClothSize clothingItemSize;

    private String source;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Gender cannot be blank")
    private Gender gender;

    public static ClothingItems toClothingEntity(ClothingItem newClothingItem){
        ClothingItems clothItemEntity = new ClothingItems();
        clothItemEntity.setDescription(newClothingItem.getDescription());
        clothItemEntity.setType(newClothingItem.getType());
        clothItemEntity.setItemCondition(newClothingItem.getItemCondition());
        clothItemEntity.setClothingItemSize(newClothingItem.getClothingItemSize());
        clothItemEntity.setSource(newClothingItem.getSource());
        clothItemEntity.setName(newClothingItem.getName());
        clothItemEntity.setGender(newClothingItem.getGender());
        return clothItemEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothingItem that = (ClothingItem) o;
        return Objects.equals(description, that.description) && type == that.type && Objects.equals(itemCondition, that.itemCondition) && clothingItemSize == that.clothingItemSize && Objects.equals(source, that.source) && Objects.equals(name, that.name) && gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, type, itemCondition, clothingItemSize, source, name, gender);
    }

    @Override
    public String toString() {
        return "NewClothingItem{" +
                "description='" + description + '\'' +
                ", type=" + type +
                ", itemCondition='" + itemCondition + '\'' +
                ", clothingItemSize=" + clothingItemSize +
                ", source='" + source + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }

}
