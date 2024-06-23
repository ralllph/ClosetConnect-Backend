package com.capstone.closetconnect.models;

import com.capstone.closetconnect.dtos.ClothingItemsDto;
import com.capstone.closetconnect.enums.ClothSize;
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

    @ManyToOne
    private User user;

    @Column(name = "photo_url")
    private String photoUrl;

    private String descriptiom;

    @Column(name = "type")
    private String type;

    @Column(name = "item_condition")
    private String itemCondition;

    @Column(name = "clothing_item_size")
    @Enumerated(EnumType.STRING)
    private ClothSize clothingItemSize;

    @Column(name = "status")
    private String status = "AVAILABLE";

    @Column(name = "source")
    private String source;

    @OneToMany(mappedBy = "clothingItems" ,fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Bid> bids;

    @ManyToOne
    private Donations donations;

    @OneToMany(mappedBy = "clothingItem" ,fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Report> reports;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    public static ClothingItemsDto toClothingItemEntity(ClothingItems clothingItems){
        ClothingItemsDto clothingItemsDto = new ClothingItemsDto();
        clothingItemsDto.setPhotoUrl(clothingItemsDto.getPhotoUrl());
        clothingItemsDto.setDescription(clothingItemsDto.getDescription());
        clothingItemsDto.setType(clothingItemsDto.getType());
        clothingItemsDto.setItemCondition(clothingItemsDto.getItemCondition());
        clothingItemsDto.setClothingItemSize(clothingItemsDto.getClothingItemSize());
        clothingItemsDto.setStatus(clothingItemsDto.getStatus());
        clothingItemsDto.setSource(clothingItemsDto.getSource());
        return clothingItemsDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothingItems that = (ClothingItems) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(photoUrl, that.photoUrl) && Objects.equals(descriptiom, that.descriptiom) && Objects.equals(type, that.type) && Objects.equals(itemCondition, that.itemCondition) && Objects.equals(clothingItemSize, that.clothingItemSize) && Objects.equals(status, that.status) && Objects.equals(source, that.source) && Objects.equals(bids, that.bids) && Objects.equals(donations, that.donations) && Objects.equals(reports, that.reports) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, photoUrl, descriptiom, type, itemCondition, clothingItemSize, status, source, bids, donations, reports, createdAt);
    }

    @Override
    public String toString() {
        return "ClothingItems{" +
                "id=" + id +
                ", user=" + user +
                ", photoUrl='" + photoUrl + '\'' +
                ", descriptiom='" + descriptiom + '\'' +
                ", type='" + type + '\'' +
                ", itemCondition='" + itemCondition + '\'' +
                ", clothingItemSize='" + clothingItemSize + '\'' +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", bids=" + bids +
                ", donations=" + donations +
                ", reports=" + reports +
                ", createdAt=" + createdAt +
                '}';
    }
}
