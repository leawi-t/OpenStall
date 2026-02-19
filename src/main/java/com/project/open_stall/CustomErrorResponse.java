package com.project.open_stall;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class CustomErrorResponse {

    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String message;
    private List<String> fieldErrors;

    CustomErrorResponse(LocalDateTime timeStamp, int status, String error, String message){
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public void addFieldError(String field, String error){
        fieldErrors.add(field + ": " + error);
    }
}