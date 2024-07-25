package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.models.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository  extends JpaRepository<Notifications, Long> {

    @Query("SELECT n FROM Notifications n WHERE n.user.id=:userId ORDER BY n.createdAt DESC")
    Optional<List<Notifications>> findByUserId(@Param("userId") Long userId);

}
