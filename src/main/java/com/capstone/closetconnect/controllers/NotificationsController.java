package com.capstone.closetconnect.controllers;

import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.dtos.response.NotifDetails;
import com.capstone.closetconnect.services.notifications.NotifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@Slf4j
public class NotificationsController {

    private final NotifService notifService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotifDetails>> getUserNotifications
            (@PathVariable("userId") Long userId)
    {
        return new ResponseEntity<>(notifService.listNotifByUser(userId), HttpStatus.OK);
    }

    @PutMapping("/{notifId}")
    public ResponseEntity<ActionSuccess> markNotifRead
            (@PathVariable("notifId") Long notifId)
    {
        return new ResponseEntity<>(notifService.markNotificationRead(notifId), HttpStatus.OK);
    }
}
