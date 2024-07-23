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
import com.capstone.closetconnect.services.notifications.NotifService;
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

    private final NotifService notifService;

    @Override
    public ActionSuccess requestTrade(RequestTrade tradeRequest) {

        long itemRequestedId = tradeRequest.getItemRequestedId();
        Long tradeInitiatorId =tradeRequest.getTradeInitiatorId();
        Long tradeInitiatorClothId = tradeRequest.getInitiatorItemId();
        Long userToTradeWithId = tradeRequest.getUserToTradeWithId();
        ClothingItems tradeInitiatorCloth = clothingItemsService.checkClothItemExists(tradeInitiatorClothId);
        ClothingItems clothRequested = clothingItemsService.checkClothItemExists(itemRequestedId);
        User tradeInitiator = checkUserExist(tradeRequest.getTradeInitiatorId());
        User userToTradeWith = checkUserExist(tradeRequest.getUserToTradeWithId());

        //check both trading parties own respective items
        if(!tradeInitiatorCloth.getUser().getId().equals(tradeInitiatorId)
                || !clothRequested.getUser().getId().equals(userToTradeWithId)
        ){
            throw new NotAssociatedException("clothing item", "user");
        }

        //check user is not attempting self trade
        if(userToTradeWithId.equals(tradeInitiatorId))
            throw new SelfTradeException();

        //TODO: send async email notification to receiver
        //TODO: send confirmation email notification to sender
        //TODO: save in app notification for receiver
        String traderInitiatorName = tradeInitiator.getName();
        String tradeInitiatorClothName = tradeInitiatorCloth.getName();
        String clothRequestedName  =  clothRequested.getName();
        String notifiMessageToReceiver = createNotifiMessage(traderInitiatorName,
                tradeInitiatorClothName, clothRequestedName);
        notifService.createNotification(userToTradeWith, notifiMessageToReceiver);
        //TODO: client side to link to trades page for that user when a particular notification is clcked
        //TODO: if user accepts/rejects send async email notification to sender and inapp notif to sender
        //TODO: create service for get notifications based on user
        ActionSuccess successMessage = new ActionSuccess();
        successMessage.setMessage("Trade request Sent Successfully");
        return successMessage;
    }
    public void tradeCloth(RequestTrade tradeRequest){
    }

    private User checkUserExist(Long userId){
        return userRepo.findById(userId)
                .orElseThrow(()-> new NotFoundException("user", userId));
    }

    private String createNotifiMessage(String tradeInitiator, String tradeInitiatorCloth, String clothRequested){
        return tradeInitiator + " " + "is trying to trade "+ " " + tradeInitiatorCloth + " " + "for your"
                + " " + clothRequested;
    }

}
