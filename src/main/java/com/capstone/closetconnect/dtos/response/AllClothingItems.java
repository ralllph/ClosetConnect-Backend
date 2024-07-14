package com.capstone.closetconnect.dtos.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class AllClothingItems {

    private Long id;

    private String name;

    private String description;

    private String photoUrl;

    private String status;

    private Long userId;

    private String userFullName;

    private LocalDate createdAt;

    public AllClothingItems(Long id, String name, String description, String photoUrl, String status, Long userId,
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
