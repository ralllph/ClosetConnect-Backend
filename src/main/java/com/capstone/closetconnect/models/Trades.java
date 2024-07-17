package com.capstone.closetconnect.models;

import com.capstone.closetconnect.enums.TradeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TradeStatus status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
