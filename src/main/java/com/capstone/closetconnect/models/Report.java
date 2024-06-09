package com.capstone.closetconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "reports")
public class Report  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private User reportedBy;

    @ManyToOne
    private User reportedUser;

    @ManyToOne
    private ClothingItems clothingItem;

    private String reason;

    private String status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id) && Objects.equals(reportedBy, report.reportedBy) && Objects.equals(reportedUser, report.reportedUser) && Objects.equals(clothingItem, report.clothingItem) && Objects.equals(reason, report.reason) && Objects.equals(status, report.status) && Objects.equals(createdAt, report.createdAt) && Objects.equals(resolvedAt, report.resolvedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportedBy, reportedUser, clothingItem, reason, status, createdAt, resolvedAt);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportedBy=" + reportedBy +
                ", reportedUser=" + reportedUser +
                ", clothingItem=" + clothingItem +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", resolvedAt=" + resolvedAt +
                '}';
    }
}
