package com.capstone.closetconnect.models;

import com.capstone.closetconnect.enums.TradeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @OneToOne(mappedBy = "trade", cascade = CascadeType.ALL)
    private Notifications notification;

    @Column(name = "exchange_date")
    private LocalDateTime exchangeDate;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
