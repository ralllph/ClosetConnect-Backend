package com.capstone.closetconnect.services.notifications;

import com.capstone.closetconnect.models.Notifications;
import com.capstone.closetconnect.models.User;
import com.capstone.closetconnect.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotifServiceImpl implements  NotifService{

    private final NotificationRepository notifRepo;

    @Override
    public void createNotification(User user, String message) {

        Notifications newNotif = new Notifications();
        newNotif.setMessage(message);
        newNotif.setUser(user);
        notifRepo.save(newNotif);
    }

}
