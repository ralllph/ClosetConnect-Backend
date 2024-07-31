package com.capstone.closetconnect.services.notifications;

import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.dtos.response.NotifDetails;
import com.capstone.closetconnect.enums.NotificationStatus;
import com.capstone.closetconnect.exceptions.NotFoundException;
import com.capstone.closetconnect.models.ClothingItems;
import com.capstone.closetconnect.models.Notifications;
import com.capstone.closetconnect.models.Trades;
import com.capstone.closetconnect.models.User;
import com.capstone.closetconnect.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotifServiceImpl implements  NotifService{

    private final NotificationRepository notifRepo;

    @Override
    public Notifications createNotification(User user, String message) {
        Notifications newNotif = new Notifications();
        newNotif.setMessage(message);
        newNotif.setUser(user);
        return notifRepo.save(newNotif);
    }

    @Override
    public ActionSuccess markNotificationRead(Long notifId) {
        Notifications notifToUpdate = findNotifById(notifId);
        notifToUpdate.setStatus(NotificationStatus.READ);
        notifRepo.save(notifToUpdate);
        return new ActionSuccess("notification marked as viewed");
    }

    @Override
    public Notifications findNotifById(Long notifId) {
        return notifRepo.findById(notifId)
                .orElseThrow(()->new NotFoundException("notification", notifId));
    }

    @Override
    public List<NotifDetails> listNotifByUser(Long userId) {
        List<Notifications> foundNotifs = notifRepo.findNotifsByUserId(userId)
                .orElseThrow(()-> new NotFoundException("notification for user", userId));
        return notifListToDtos(foundNotifs);
    }

    @Override
    public void linkNotificationToTrade(Notifications notif, Trades trades) {
        notif.setTrade(trades);
        notifRepo.save(notif);
    }

    private List<NotifDetails> notifListToDtos(List<Notifications> notifList){
        return notifList.stream()
                .map(Notifications::toNotifDto)
                .collect(Collectors.toList());
    }

}
