package com.capstone.closetconnect.dtos.request;

import com.capstone.closetconnect.enums.TradeStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeTradeStatus {

    @NotNull(message = "please provide a trade status ")
    private TradeStatus status;

    private String reason;
}
