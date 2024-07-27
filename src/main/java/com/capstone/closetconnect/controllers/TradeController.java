package com.capstone.closetconnect.controllers;

import com.capstone.closetconnect.dtos.request.RequestTrade;
import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.dtos.response.TradeDetails;
import com.capstone.closetconnect.exceptions.PaginationException;
import com.capstone.closetconnect.services.trades.TradesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trades")
@Slf4j
public class TradeController {

    private final TradesService tradesService;

    @PostMapping("/requestTrade")
    public ResponseEntity<ActionSuccess> requestTrade(@RequestBody @Valid RequestTrade tradeRequest){
        return new ResponseEntity<>(tradesService.requestTrade(tradeRequest), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/sentTrades")
    public ResponseEntity<Page<TradeDetails>> getUserSentTrades(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        checkPagination(page,size);
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(tradesService.getUserSentTrades(userId,pageable),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/receivedTrades")
    public ResponseEntity<Page<TradeDetails>> getUserReceivedDetails(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        checkPagination(page,size);
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(tradesService.getUserReceivedTrades(userId,pageable),
                HttpStatus.OK);
    }

    @GetMapping("/getTrade/{tradeId}")
    public ResponseEntity<TradeDetails> getTrade(@PathVariable("tradeId") Long tradeId){
        return new ResponseEntity<>(tradesService.getTrade(tradeId), HttpStatus.OK);
    }

    private void checkPagination(int pageNumber, int pageSize){
        if(pageNumber<0 || pageSize <1)
            throw new PaginationException();
    }
}
