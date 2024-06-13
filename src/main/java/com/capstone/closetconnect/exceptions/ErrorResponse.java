package com.capstone.closetconnect.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ErrorResponse {
    private List<String> message;
    private String timestamp;

    //giving our timestamp a nice pattern
    DateTimeFormatter timeStampPattern  = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    public ErrorResponse(List<String> message) {
        this.message = message;
        this.timestamp = timeStampPattern.format(LocalDateTime.now());
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
