package com.capstone.closetconnect.services.trades;

import com.capstone.closetconnect.dtos.request.ChangeTradeStatus;
import com.capstone.closetconnect.dtos.request.RequestTrade;
import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.dtos.response.TradeDetails;
import com.capstone.closetconnect.enums.Status;
import com.capstone.closetconnect.enums.TradeStatus;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class TradeServiceImpl implements  TradesService{

    private final UserRepository userRepo;

    private  final TradeRepository tradeRepo;

    private final ClothingItemsService clothingItemsService;

    private final ClothingItemsRepository clothRepo;

    private final NotifService notifService;

    private final EmailService emailService;

    @Override
    public ActionSuccess requestTrade(RequestTrade tradeRequest) {

        Long itemRequestedId = tradeRequest.getItemRequestedId();
        Long tradeInitiatorId = tradeRequest.getTradeInitiatorId();
        Long tradeInitiatorClothId = tradeRequest.getInitiatorItemId();
        Long userToTradeWithId = tradeRequest.getUserToTradeWithId();
        String message = tradeRequest.getMessage();
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
                clothRequested,exchangeLocation,exchangeDate,message);
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
                clothRequested,exchangeLocation,exchangeDate,message);
        prepareAndSendEmails(tradeInitiator, userToTradeWith, notificationMessage);

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
                                              LocalDateTime exchangeDate,
                                              String message) {
        String traderInitiatorName = tradeInitiator.getName();
        String tradeInitiatorClothName = tradeInitiatorCloth.getName();
        String clothRequestedName = clothRequested.getName();
        return createNotifiMessage(traderInitiatorName, tradeInitiatorClothName,
                clothRequestedName,exchangeLocation,exchangeDate,message);
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

    @Transactional
    public void tradeCloth(Long tradeId){
        Trades trade = checkTradeExists(tradeId);

        // Retrieve items and users
        ClothingItems offeredItem = trade.getOfferedItem();
        ClothingItems requestedItem = trade.getRequestedItem();

        User sender = trade.getSender();
        User receiver = trade.getReceiver();

        // mark items as unavailable for trade
        offeredItem.setStatus(Status.NOT_AVAILABLE);
        requestedItem.setStatus(Status.NOT_AVAILABLE);

        // Swap items between users
        offeredItem.setUser(receiver);
        requestedItem.setUser(sender);


        // Save changes
        clothRepo.save(offeredItem);
        clothRepo.save(requestedItem);

        // Update the trade status
        trade.setStatus(TradeStatus.COMPLETED);

        // Save the trade
        tradeRepo.save(trade);
    }

    @Override
    public Page<TradeDetails> getUserSentTrades(Long userId, Pageable pageable) {
        checkUserExist(userId);
        return tradeRepo.getUserSentTradeDetails(userId,pageable);
    }

    @Override
    public Page<TradeDetails> getUserReceivedTrades(Long userId, Pageable pageable) {
        checkUserExist(userId);
        return tradeRepo.getUserReceivedDetails(userId,pageable);
    }

    @Override
    public TradeDetails getTrade(Long tradeId) {
        checkTradeExists(tradeId);
        return tradeRepo.getTrade(tradeId)
                .orElseThrow(()-> new NotFoundException("trade",tradeId));
    }

    @Override
    public Trades checkTradeExists(Long tradeId) {
        return tradeRepo.findById(tradeId)
                .orElseThrow(()-> new NotFoundException("trade",tradeId));
    }

    @Override
    public ActionSuccess updateTradeStatus(Long receiverId,Long tradeId,
                                           ChangeTradeStatus status) {

        checkUserExist(receiverId);
        Trades tradeToUpdate = checkTradeExists(tradeId);

        if(tradeToUpdate.getReceiver().getId().equals(receiverId)){

            Trades updatedTrade = updateTradeStatusEntity(tradeToUpdate, status);
            String subject= "Trade Notification";
            String senderName = tradeToUpdate.getSender().getName();
            String senderEmail = tradeToUpdate.getSender().getEmail();
            String receiverName = tradeToUpdate.getReceiver().getName();
            String itemRequestedName = tradeToUpdate.getRequestedItem().getName();

            if(updatedTrade.getStatus().equals(TradeStatus.APPROVED)){
                tradeCloth(tradeId);
                //create notif  both parties of success
                String receiverNotifMessage = "Your successfully accepted a trade from "
                        + senderName + " " +
                        customAcceptanceMessage(tradeToUpdate);
                String senderNotifMessage = "Your trade request to " + " " +
                        receiverName + " was accepted" + " " +
                        customAcceptanceMessage(tradeToUpdate);


                Notifications receiverNotif = notifService.
                        createNotification(tradeToUpdate.getReceiver(),receiverNotifMessage);
                notifService.linkNotificationToTrade(receiverNotif,tradeToUpdate);

                //sender side
                Notifications senderNotif = notifService.
                        createNotification(tradeToUpdate.getSender(),senderNotifMessage);
                notifService.linkNotificationToTrade(senderNotif,tradeToUpdate);

                //sender email
                Map<String, String> senderEmailVariables = emailService.formEmailBody(senderName,
                        senderNotifMessage,
                        subject, "email-template");
                Map<String, Object> senderVariables = emailService
                        .formTemplateVariables(senderName,
                                senderNotifMessage);
                emailService.sendEmail(senderEmail, senderEmailVariables.get("subject"),
                        senderEmailVariables.get("templateName"),
                        senderVariables);

                //receiver email
                Map<String, String> receiverEmailVariables = emailService
                        .formEmailBody(receiverName,
                        receiverNotifMessage,
                        subject, "email-template");
                Map<String, Object> receiverVariables = emailService
                        .formTemplateVariables(receiverName,
                                receiverNotifMessage);
                emailService.sendEmail(receiverName, receiverEmailVariables.get("subject"),
                        receiverEmailVariables.get("templateName"),
                        receiverVariables);
            }
            if(updatedTrade.getStatus().equals(TradeStatus.REJECTED)){
                tradeToUpdate.setStatus(TradeStatus.REJECTED);
                //create notif  both parties for failure
                String receiverNotifMessage = "Your rejected a trade with "
                        + senderName + " " +
                        "for your " + " " + itemRequestedName;
                String senderNotifMessage = "Your trade request to" +
                        receiverName + " " + "for " + itemRequestedName + " "
                        + "was declined. " + showReason(status) ;


                Notifications receiverNotif = notifService.
                        createNotification(tradeToUpdate.getReceiver(),receiverNotifMessage);
                notifService.linkNotificationToTrade(receiverNotif,tradeToUpdate);

                //sender side
                Notifications senderNotif = notifService.
                        createNotification(tradeToUpdate.getSender(),senderNotifMessage);
                notifService.linkNotificationToTrade(senderNotif,tradeToUpdate);

                //sender email
                Map<String, String> senderEmailVariables = emailService.formEmailBody(senderName,
                        senderNotifMessage,
                        subject, "email-template");
                Map<String, Object> senderVariables = emailService
                        .formTemplateVariables(senderName,
                                senderNotifMessage);
                emailService.sendEmail(senderEmail, senderEmailVariables.get("subject"),
                        senderEmailVariables.get("templateName"),
                        senderVariables);

                //receiver email
                Map<String, String> receiverEmailVariables = emailService
                        .formEmailBody(receiverName,
                        receiverNotifMessage,
                        subject, "email-template");
                Map<String, Object> receiverVariables = emailService
                        .formTemplateVariables(receiverName,
                                receiverNotifMessage);
                emailService.sendEmail(receiverName, receiverEmailVariables.get("subject"),
                        receiverEmailVariables.get("templateName"),
                        receiverVariables);
            }
        }else{
            throw new NotAssociatedException("trade","user, user not the receiving party");
        }
        return new ActionSuccess("Trade status updated successfully");
    }

    private String showReason(ChangeTradeStatus statusWithReason){
        String reason = statusWithReason.getReason();
        return reason == null ? "" : "Reason for Rejection: " + reason;
    }

    private String customAcceptanceMessage(Trades trade){
        return "Kindly meet at" +
                trade.getExchangeLocation() + " on " +
                trade.getExchangeDate().toLocalDate() +
                "at " + " " +  trade.getExchangeDate().toLocalTime() +
                " to complete the exchange";
    }


    private Trades updateTradeStatusEntity(Trades tradeToUpdate, ChangeTradeStatus newStatus){
        if(newStatus!=null){
            tradeToUpdate.setStatus(newStatus.getStatus());
        }
        return tradeToUpdate;
    }

    private User checkUserExist(Long userId){
        return userRepo.findById(userId)
                .orElseThrow(()-> new NotFoundException("user", userId));
    }

    private String createNotifiMessage(String tradeInitiator, String tradeInitiatorCloth,
                                       String clothRequested, String exchangeLocation,
                                       LocalDateTime exchangeDate, String message){
        return tradeInitiator + " " + "wants to trade "+ " " + tradeInitiatorCloth + " "
                + "for your"
                + " " + clothRequested + " " +  "at" + " " + exchangeLocation + " " + "on"
                +  " " + exchangeDate.toLocalDate() + " " + "at " +  exchangeDate.toLocalTime() +
                "." + " " + checkUserLeftNote(tradeInitiator,message) ;
    }

    private String checkUserLeftNote(String tradeInitiator, String message){
        return message != null ? "A short note from " + " " + tradeInitiator + ":"
                + " " + "'"+message+"'" : "";
    }


}
