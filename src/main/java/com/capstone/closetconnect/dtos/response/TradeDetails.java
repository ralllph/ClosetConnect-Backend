package com.capstone.closetconnect.dtos.response;

import com.capstone.closetconnect.enums.TradeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TradeDetails {

    private Long tradeId;
    private String senderName;
    private String receiverName;
    private String offeredItemName;
    private String requestedItemName;
    private TradeStatus status;
    private String exchangeLocation;
    private LocalDateTime exchangeDate;
    private LocalDateTime createdAt;
    private Long tradersItemId;
    private Long requestedItemId;

    public TradeDetails(Long tradeId, String senderName, String receiverName,
                        String offeredItemName, String requestedItemName,
                        TradeStatus status, String exchangeLocation,
                        Long requestedItemId, Long tradersItemId,
                        LocalDateTime exchangeDate, Timestamp createdAt) {
        this.tradeId = tradeId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.offeredItemName = offeredItemName;
        this.requestedItemName = requestedItemName;
        this.status = status;
        this.requestedItemId = requestedItemId;
        this.tradersItemId = tradersItemId;
        this.exchangeLocation = exchangeLocation;
        this.exchangeDate = exchangeDate;
        this.createdAt = createdAt.toLocalDateTime();
    }
}
