package com.capstone.closetconnect.services.notifications;

import com.capstone.closetconnect.models.Notifications;
import com.capstone.closetconnect.models.User;

public interface NotifService {

    void createNotification( User user, String message);

}
