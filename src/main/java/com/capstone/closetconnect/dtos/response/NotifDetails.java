package com.capstone.closetconnect.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifDetails {

    private Long id;

    private String message;

    private LocalDate date;

    private LocalTime time;

    private Long tradeId;

    @Override
    public String toString() {
        return "NotifDetails{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
