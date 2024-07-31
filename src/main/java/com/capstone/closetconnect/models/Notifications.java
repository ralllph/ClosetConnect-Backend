package com.capstone.closetconnect.models;

import com.capstone.closetconnect.dtos.response.NotifDetails;
import com.capstone.closetconnect.enums.NotificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.UNREAD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id")
    private Trades trade;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    public static NotifDetails toNotifDto(Notifications notifEntity){
        NotifDetails notifDto = new NotifDetails();
        notifDto.setId(notifEntity.getId());
        notifDto.setMessage(notifEntity.getMessage());
        notifDto.setNotificationStatus(notifEntity.getStatus());
        notifDto.setDate(notifEntity.getCreatedAt()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        notifDto.setTime(notifEntity.getCreatedAt()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        notifDto.setTradeId(notifEntity.getTrade() != null ? notifEntity.getTrade().getId() : null);
        return  notifDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notifications that = (Notifications) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(message, that.message) && status == that.status && Objects.equals(trade, that.trade) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, message, status, trade, createdAt);
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "id=" + id +
                ", user=" + user +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
