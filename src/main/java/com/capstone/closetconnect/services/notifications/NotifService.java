package com.capstone.closetconnect.services.notifications;

import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.dtos.response.NotifDetails;
import com.capstone.closetconnect.models.Notifications;
import com.capstone.closetconnect.models.User;

import java.util.List;
import java.util.Optional;

public interface NotifService {

    void createNotification( User user, String message);

    ActionSuccess markNotificationRead(Long notifId);

    Notifications findNotifById(Long notifId);

    List<NotifDetails>  listNotifByUser(Long userId);
}
