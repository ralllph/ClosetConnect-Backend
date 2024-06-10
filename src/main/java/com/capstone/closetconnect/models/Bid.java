package com.capstone.closetconnect.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Table(name = "bids")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bid implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private ClothingItems clothingItems;

    @ManyToOne
    private User user;

    @Column(name = "bid_amount")
    private BigDecimal bidAmount;

    @Column(name = "bid_status")
    private String bidStatus; //create enum

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return Objects.equals(id, bid.id) && Objects.equals(clothingItems, bid.clothingItems) && Objects.equals(user, bid.user) && Objects.equals(bidAmount, bid.bidAmount) && Objects.equals(bidStatus, bid.bidStatus) && Objects.equals(createdAt, bid.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clothingItems, user, bidAmount, bidStatus, createdAt);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", clothingItems=" + clothingItems +
                ", user=" + user +
                ", bidAmount=" + bidAmount +
                ", bidStatus='" + bidStatus + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
