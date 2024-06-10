package com.capstone.closetconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Table(name = "ratings")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ratings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "rated_by")
    private User ratedBy;

    @ManyToOne
    @JoinColumn(name = "rated_user")
    private User ratedUser;

    @Column(name = "rating_value")
    private int ratingValue;

    private String review;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ratings ratings = (Ratings) o;
        return ratingValue == ratings.ratingValue && Objects.equals(id, ratings.id) && Objects.equals(ratedBy, ratings.ratedBy) && Objects.equals(ratedUser, ratings.ratedUser) && Objects.equals(review, ratings.review) && Objects.equals(createdAt, ratings.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ratedBy, ratedUser, ratingValue, review, createdAt);
    }

    @Override
    public String toString() {
        return "Ratings{" +
                "id=" + id +
                ", ratedBy=" + ratedBy +
                ", ratedUser=" + ratedUser +
                ", ratingValue=" + ratingValue +
                ", review='" + review + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
