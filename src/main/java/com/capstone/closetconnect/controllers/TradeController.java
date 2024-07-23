package com.capstone.closetconnect.controllers;

import com.capstone.closetconnect.dtos.request.RequestTrade;
import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.services.trades.TradesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
