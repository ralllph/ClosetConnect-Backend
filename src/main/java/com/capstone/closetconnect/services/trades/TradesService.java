package com.capstone.closetconnect.services.trades;

import com.capstone.closetconnect.dtos.request.RequestTrade;
import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.dtos.response.TradeDetails;
import com.capstone.closetconnect.models.Trades;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TradesService {

    ActionSuccess requestTrade(RequestTrade tradeRequest);
    void tradeCloth(RequestTrade tradeRequest);

    Page<TradeDetails> getUserSentTrades(Long userId, Pageable pageable);

    Page<TradeDetails> getUserReceivedTrades(Long userId, Pageable pageable);

    TradeDetails getTrade(Long tradeId);

    Trades checkTradeExists(Long tradeId);
}
