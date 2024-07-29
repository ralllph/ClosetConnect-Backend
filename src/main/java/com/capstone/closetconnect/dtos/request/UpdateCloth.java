package com.capstone.closetconnect.dtos.request;

import com.capstone.closetconnect.enums.ClothSize;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCloth {

    private String description;

    private ClothType type;

    private String itemCondition;

    private ClothSize clothingItemSize;

    private String source;

    private Status status;

    private String name;

    private Gender gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateCloth that = (UpdateCloth) o;
        return Objects.equals(description, that.description) && type == that.type && Objects.equals(itemCondition, that.itemCondition) && clothingItemSize == that.clothingItemSize && Objects.equals(source, that.source) && status == that.status && Objects.equals(name, that.name) && gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, type, itemCondition, clothingItemSize, source, status, name, gender);
    }

    @Override
    public String toString() {
        return "UpdateCloth{" +
                "description='" + description + '\'' +
                ", type=" + type +
                ", itemCondition='" + itemCondition + '\'' +
                ", clothingItemSize=" + clothingItemSize +
                ", source='" + source + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
