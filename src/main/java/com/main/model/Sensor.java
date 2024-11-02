package com.main.model;

import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

public class Sensor {
    private String type;
    private String value;

    @CreatedDate
    private LocalDateTime timestamp;

    public Sensor() {}

    public Sensor(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}