package com.capstone.closetconnect.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

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

}
