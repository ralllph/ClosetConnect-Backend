package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.models.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository  extends JpaRepository<Notifications, Long> {

    Optional<List<Notifications>> findByUserId(Long userId);

}
