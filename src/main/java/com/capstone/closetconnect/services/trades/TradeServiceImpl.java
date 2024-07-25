package com.capstone.closetconnect.services.trades;

import com.capstone.closetconnect.dtos.request.RequestTrade;
import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.exceptions.NotAssociatedException;
import com.capstone.closetconnect.exceptions.NotFoundException;
import com.capstone.closetconnect.exceptions.SelfTradeException;
import com.capstone.closetconnect.models.ClothingItems;
import com.capstone.closetconnect.models.Notifications;
import com.capstone.closetconnect.models.Trades;
import com.capstone.closetconnect.models.User;
import com.capstone.closetconnect.repositories.ClothingItemsRepository;
import com.capstone.closetconnect.repositories.TradeRepository;
import com.capstone.closetconnect.repositories.UserRepository;
import com.capstone.closetconnect.services.clothing_items.ClothingItemsService;
import com.capstone.closetconnect.services.email.EmailService;
import com.capstone.closetconnect.services.notifications.NotifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradeServiceImpl implements  TradesService{

    private final UserRepository userRepo;

    private  final TradeRepository tradeRepo;

    private final ClothingItemsService clothingItemsService;

    private final NotifService notifService;

    private final EmailService emailService;

    @Override
    public ActionSuccess requestTrade(RequestTrade tradeRequest) {

        Long itemRequestedId = tradeRequest.getItemRequestedId();
        Long tradeInitiatorId = tradeRequest.getTradeInitiatorId();
        Long tradeInitiatorClothId = tradeRequest.getInitiatorItemId();
        Long userToTradeWithId = tradeRequest.getUserToTradeWithId();
        String exchangeLocation = tradeRequest.getExchangeLocation();
        LocalDateTime exchangeDate = tradeRequest.getExchangeDate();


        // Fetch required entities
        ClothingItems tradeInitiatorCloth = clothingItemsService
                .checkClothItemExists(tradeInitiatorClothId);
        ClothingItems clothRequested = clothingItemsService
                .checkClothItemExists(itemRequestedId);
        User tradeInitiator = checkUserExist(tradeInitiatorId);
        User userToTradeWith = checkUserExist(userToTradeWithId);

        // Validate ownership and trade conditions
        validateTradeConditions(tradeInitiatorCloth, tradeInitiatorId,
                clothRequested,
                userToTradeWithId);

        //create notif for receiver
        String receiverNotifMessage = prepareNotificationMessage(tradeInitiator,
                tradeInitiatorCloth,
                clothRequested,exchangeLocation,exchangeDate);
        Notifications receiverNotif = notifService.
                createNotification(userToTradeWith,receiverNotifMessage);


        //create Trade
        Trades  newTrade=  createNewTrade(tradeInitiator,tradeInitiatorCloth,
                userToTradeWith, clothRequested,exchangeLocation,exchangeDate);

        //link notification to trade
        notifService.linkNotificationToTrade(receiverNotif,newTrade);
        // Prepare notifications and emails
        String notificationMessage = prepareNotificationMessage(tradeInitiator,
                tradeInitiatorCloth,
                clothRequested,exchangeLocation,exchangeDate);
        prepareAndSendEmails(tradeInitiator, userToTradeWith, notificationMessage);

        // TODO: Create new trade object and update respective entities
        // TODO: Save in-app notification for receiver
        // TODO: Mark notification as read if user clicks
        // TODO: Create service for getting notifications based on user
        //TODO: LINK NOTIFICATION TO TRADE


        // TODO: Client side to link to trades page for that user when a particular
        //  notification is clicked
        // TODO: If user accepts/rejects send async email notification to sender
        //  and in-app notification to sender
        //TODO:  Hide email details in env variable
        //TODO: perform trade if user accepts
        // Return success response
        return new ActionSuccess("Trade request Sent Successfully");
    }

    private Trades createNewTrade(User tradeInitiator, ClothingItems tradeInitiatorCloth,
                                  User userToTradeWith, ClothingItems clothRequested,
                                  String exchangeLocation, LocalDateTime exchangeDate) {
        Trades newTrade = new Trades();
        newTrade.setSender(tradeInitiator);
        newTrade.setReceiver(userToTradeWith);
        newTrade.setOfferedItem(tradeInitiatorCloth);
        newTrade.setRequestedItem(clothRequested);
        newTrade.setExchangeDate(exchangeDate);
        newTrade.setExchangeLocation(exchangeLocation);
        return  tradeRepo.save(newTrade);
    }

    private void validateTradeConditions(ClothingItems tradeInitiatorCloth,
                                         Long tradeInitiatorId,
                                         ClothingItems clothRequested,
                                         Long userToTradeWithId) {
        if (!tradeInitiatorCloth.getUser().getId().equals(tradeInitiatorId) ||
                !clothRequested.getUser().getId().equals(userToTradeWithId)) {
            throw new NotAssociatedException("clothing item", "user");
        }
        if (userToTradeWithId.equals(tradeInitiatorId)) {
            throw new SelfTradeException();
        }
    }

    private String prepareNotificationMessage(User tradeInitiator,
                                              ClothingItems tradeInitiatorCloth,
                                              ClothingItems clothRequested,
                                              String exchangeLocation,
                                              LocalDateTime exchangeDate) {
        String traderInitiatorName = tradeInitiator.getName();
        String tradeInitiatorClothName = tradeInitiatorCloth.getName();
        String clothRequestedName = clothRequested.getName();
        return createNotifiMessage(traderInitiatorName, tradeInitiatorClothName,
                clothRequestedName,exchangeLocation,exchangeDate);
    }



    private void prepareAndSendEmails(User tradeInitiator, User userToTradeWith,
                                      String notificationMessage) {
        String tradeInitiatorEmail = tradeInitiator.getEmail();
        String tradeInitiatorName = tradeInitiator.getName();
        String userToTradeWithEmail = userToTradeWith.getEmail();
        String userToTradeWithName = userToTradeWith.getName();
        String subject = "Trade Request Notification";

        Map<String, String> emailVariables = emailService.formEmailBody(userToTradeWithName,
                notificationMessage,
                subject, "email-template");
        Map<String, Object> receiverVariables = emailService
                .formTemplateVariables(userToTradeWithName,
                notificationMessage);
        emailService.sendEmail(userToTradeWithEmail, emailVariables.get("subject"),
                emailVariables.get("templateName"),
                receiverVariables);

        String confirmationMessage = "Your trade request to " + userToTradeWithName
                + " was sent out";
        Map<String, Object> senderVariables = emailService
                .formTemplateVariables(tradeInitiatorName,
                confirmationMessage);
        emailService.sendEmail(tradeInitiatorEmail, emailVariables.get("subject"),
                emailVariables.get("templateName"),
                senderVariables);
    }

    public void tradeCloth(RequestTrade tradeRequest){
    }

    private User checkUserExist(Long userId){
        return userRepo.findById(userId)
                .orElseThrow(()-> new NotFoundException("user", userId));
    }

    private String createNotifiMessage(String tradeInitiator, String tradeInitiatorCloth,
                                       String clothRequested, String exchangeLocation,
                                       LocalDateTime exchangeDate){
        return tradeInitiator + " " + "wants to trade "+ " " + tradeInitiatorCloth + " "
                + "for your"
                + " " + clothRequested + " " +  "at" + " " + exchangeLocation + " " + "on"
                +  " " + exchangeDate.toLocalDate() + " " + "at " +  exchangeDate.toLocalTime();
    }


}
