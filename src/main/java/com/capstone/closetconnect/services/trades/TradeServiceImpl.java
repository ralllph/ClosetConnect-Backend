package com.capstone.closetconnect.services.trades;

import com.capstone.closetconnect.dtos.request.RequestTrade;
import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.exceptions.NotAssociatedException;
import com.capstone.closetconnect.exceptions.NotFoundException;
import com.capstone.closetconnect.exceptions.SelfTradeException;
import com.capstone.closetconnect.models.ClothingItems;
import com.capstone.closetconnect.models.User;
import com.capstone.closetconnect.repositories.ClothingItemsRepository;
import com.capstone.closetconnect.repositories.UserRepository;
import com.capstone.closetconnect.services.clothing_items.ClothingItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradeServiceImpl implements  TradesService{

    private final UserRepository userRepo;

    private  final ClothingItemsRepository clothRepo;

    private final ClothingItemsService clothingItemsService;

    @Override
    public ActionSuccess requestTrade(RequestTrade tradeRequest) {

        long itemRequestedId = tradeRequest.getItemRequestedId();
        Long tradeInitiatorId =tradeRequest.getTradeInitiatorId();
        Long tradeInitiatorClothId = tradeRequest.getInitiatorItemId();
        Long userToTradeWithId = tradeRequest.getUserToTradeWithId();
        ClothingItems tradeInitiatorCloth = clothingItemsService.checkClothItemExists(tradeInitiatorClothId);
        ClothingItems clothRequested = clothingItemsService.checkClothItemExists(itemRequestedId);
        checkUserExist(tradeRequest.getTradeInitiatorId());
        checkUserExist(tradeRequest.getUserToTradeWithId());

        //check both trading parties own respective items
        if(!tradeInitiatorCloth.getUser().getId().equals(tradeInitiatorId)
                || !clothRequested.getUser().getId().equals(userToTradeWithId)
        ){
            throw new NotAssociatedException("clothing item", "user");
        }

        //check user is not attempting self trade
        if(userToTradeWithId.equals(tradeInitiatorId))
            throw new SelfTradeException();

        //TODO: send async notification to receiver
        return null;
    }
    public void tradeCloth(RequestTrade tradeRequest){
    }

    private User checkUserExist(Long userId){
        return userRepo.findById(userId)
                .orElseThrow(()-> new NotFoundException("user", userId));
    }



}
