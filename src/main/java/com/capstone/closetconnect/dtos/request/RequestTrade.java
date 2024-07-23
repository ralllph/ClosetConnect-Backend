package com.capstone.closetconnect.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestTrade {

    private Long tradeInitiatorId;

    private Long itemRequestedId;

    private Long initiatorItemId;

    private Long userToTradeWithId;

}
