package com.capstone.closetconnect.models;

import com.capstone.closetconnect.dtos.response.ClothingItemsDto;
import com.capstone.closetconnect.enums.ClothSize;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clothing_items")
public class ClothingItems implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @Column(name = "photo_url")
    private String photoUrl;

    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ClothType type;

    @Column(name = "item_condition")
    private String itemCondition;

    @Column(name = "clothing_item_size")
    @Enumerated(EnumType.STRING)
    private ClothSize clothingItemSize;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    @Column(name = "source")
    private String source;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "clothingItems" ,fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Bid> bids;

    @ManyToOne
    @JsonIgnore
    private Donations donations;

    @OneToMany(mappedBy = "clothingItem" ,fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Report> reports;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    public static ClothingItemsDto toClothingItemDto(ClothingItems clothingItems){
        ClothingItemsDto clothingItemsDto = new ClothingItemsDto();
        clothingItemsDto.setId(clothingItems.getId());
        clothingItemsDto.setPhotoUrl(clothingItems.getPhotoUrl());
        clothingItemsDto.setDescription(clothingItems.getDescription());
        clothingItemsDto.setType(clothingItems.getType());
        clothingItemsDto.setName(clothingItems.getName());
        clothingItemsDto.setGender(clothingItems.getGender());
        clothingItemsDto.setItemCondition(clothingItems.getItemCondition());
        clothingItemsDto.setClothingItemSize(clothingItems.getClothingItemSize());
        clothingItemsDto.setStatus(clothingItems.getStatus());
        clothingItemsDto.setSource(clothingItems.getSource());
        return clothingItemsDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothingItems that = (ClothingItems) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(photoUrl, that.photoUrl) && Objects.equals(description, that.description) && Objects.equals(type, that.type) && Objects.equals(itemCondition, that.itemCondition) && clothingItemSize == that.clothingItemSize && Objects.equals(status, that.status) && Objects.equals(source, that.source) && Objects.equals(name, that.name) && gender == that.gender && Objects.equals(bids, that.bids) && Objects.equals(donations, that.donations) && Objects.equals(reports, that.reports) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, photoUrl, description, type, itemCondition, clothingItemSize, status, source, name, gender, bids, donations, reports, createdAt);
    }

    @Override
    public String toString() {
        return "ClothingItems{" +
                "id=" + id +
                ", photoUrl='" + photoUrl + '\'' +
                ", descriptiom='" + description + '\'' +
                ", type='" + type + '\'' +
                ", itemCondition='" + itemCondition + '\'' +
                ", clothingItemSize=" + clothingItemSize +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", createdAt=" + createdAt +
                '}';
    }
}
