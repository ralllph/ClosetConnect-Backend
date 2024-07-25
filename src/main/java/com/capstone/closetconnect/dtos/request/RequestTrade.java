package com.capstone.closetconnect.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestTrade {

    @NotNull(message = "tradeInitiatorId can not be null")
    private Long tradeInitiatorId;

    @NotNull(message = "itemRequestedId can not be null")
    private Long itemRequestedId;

    @NotNull(message = "initiatorItemId can not be null")
    private Long initiatorItemId;

    @NotNull(message = "userToTradeWithId can not be null")
    private Long userToTradeWithId;

    @NotNull(message = "Exchange date cannot be null")
    private LocalDateTime exchangeDate;

    @NotBlank(message = "exchange location cannot be blank")
    private String exchangeLocation;
}
