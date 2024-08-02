package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.dtos.response.TradeDetails;
import com.capstone.closetconnect.models.Trades;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trades, Long> {

    @Query("SELECT new com.capstone.closetconnect.dtos.response.TradeDetails(" +
            "t.id, t.sender.name, t.receiver.name, " +
            "t.offeredItem.name, t.requestedItem.name, " +
            "t.status, t.exchangeLocation,t.requestedItem.id,t.offeredItem.id, " +
            "t.exchangeDate, t.createdAt) " +
            "FROM Trades t " +
            "WHERE t.sender.id = :userId ORDER BY t.createdAt DESC")
    Page<TradeDetails> getUserSentTradeDetails(@Param("userId") Long userId,  Pageable pageable);

    @Query("SELECT new com.capstone.closetconnect.dtos.response.TradeDetails(" +
            "t.id, t.sender.name, t.receiver.name, " +
            "t.offeredItem.name, t.requestedItem.name, " +
            "t.status, t.exchangeLocation,t.requestedItem.id,t.offeredItem.id, " +
            "t.exchangeDate, t.createdAt) " +
            "FROM Trades t " +
            "WHERE t.receiver.id = :userId ORDER BY t.createdAt DESC")
    Page<TradeDetails> getUserReceivedDetails(@Param("userId") Long userId,  Pageable pageable);

    @Query("SELECT new com.capstone.closetconnect.dtos.response.TradeDetails(" +
            "t.id, t.sender.name, t.receiver.name, " +
            "t.offeredItem.name, t.requestedItem.name, " +
            "t.status, t.exchangeLocation,t.requestedItem.id,t.offeredItem.id, " +
            "t.exchangeDate, t.createdAt) " +
            "FROM Trades t " +
            "WHERE t.id = :tradeId ORDER BY t.createdAt DESC")
    Optional<TradeDetails> getTrade(@Param("tradeId") Long tradeId);

}
