package com.capstone.closetconnect.dtos.response;


import com.capstone.closetconnect.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ClothDetailsWithUser {

    private Long id;

    private String name;

    private String description;

    private String photoUrl;

    private Status status;

    private Long userId;

    private String userFullName;

    private LocalDate createdAt;

    public ClothDetailsWithUser(Long id, String name, String description, String photoUrl, Status status, Long userId,
                                String userFullName, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.status = status;
        this.userId = userId;
        this.userFullName = userFullName;
        this.createdAt = createdAt.toLocalDateTime().toLocalDate();
    }

    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
