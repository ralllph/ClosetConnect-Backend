package com.capstone.closetconnect.models;

import com.capstone.closetconnect.enums.TradeStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "trades")
public class Trades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offered_item_id", nullable = false)
    private ClothingItems offeredItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_item_id", nullable = false)
    private ClothingItems requestedItem;

    @Column(name = "trade_status")
    @Enumerated(EnumType.STRING)
    private TradeStatus status = TradeStatus.PENDING;

    @Column(name = "exchange_location")
    private String exchangeLocation;

    @OneToMany(mappedBy = "trade",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notifications> notification;

    @Column(name = "exchange_date")
    private LocalDateTime exchangeDate;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trades trades = (Trades) o;
        return Objects.equals(id, trades.id) && Objects.equals(sender, trades.sender) && Objects.equals(receiver, trades.receiver) && Objects.equals(offeredItem, trades.offeredItem) && Objects.equals(requestedItem, trades.requestedItem) && status == trades.status && Objects.equals(exchangeLocation, trades.exchangeLocation) && Objects.equals(notification, trades.notification) && Objects.equals(exchangeDate, trades.exchangeDate) && Objects.equals(createdAt, trades.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, receiver, offeredItem, requestedItem, status, exchangeLocation, notification, exchangeDate, createdAt);
    }

    @Override
    public String toString() {
        return "Trades{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", offeredItem=" + offeredItem +
                ", requestedItem=" + requestedItem +
                ", status=" + status +
                ", exchangeLocation='" + exchangeLocation + '\'' +
                ", notification=" + notification +
                ", exchangeDate=" + exchangeDate +
                ", createdAt=" + createdAt +
                '}';
    }

}
