package com.capstone.closetconnect.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Table(name = "donations")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Donations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "donations",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ClothingItems> clothingItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @Column(name = "claimed_by")
    private String claimedBy;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "claimed_at", updatable = false)
    private LocalDateTime claimedAAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donations donations = (Donations) o;
        return Objects.equals(id, donations.id) && Objects.equals(clothingItems, donations.clothingItems) && Objects.equals(user, donations.user) && Objects.equals(claimedBy, donations.claimedBy) && Objects.equals(createdAt, donations.createdAt) && Objects.equals(claimedAAt, donations.claimedAAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clothingItems, user, claimedBy, createdAt, claimedAAt);
    }

    @Override
    public String toString() {
        return "Donations{" +
                "id=" + id +
                ", user=" + user +
                ", claimedBy='" + claimedBy + '\'' +
                ", createdAt=" + createdAt +
                ", claimedAAt=" + claimedAAt +
                '}';
    }
}
