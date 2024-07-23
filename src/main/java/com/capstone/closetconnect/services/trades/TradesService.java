package com.capstone.closetconnect.services.trades;

import com.capstone.closetconnect.dtos.request.RequestTrade;
import com.capstone.closetconnect.dtos.response.ActionSuccess;

public interface TradesService {

    ActionSuccess requestTrade(RequestTrade tradeRequest);
    void tradeCloth(RequestTrade tradeRequest);

}
