package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.models.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository  extends JpaRepository<Notifications, Long> {
}
