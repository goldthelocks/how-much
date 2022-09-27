package com.eraine.howmuch.model.jpa;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tap {

    public enum Type {
        ON, OFF
    }

    private Long id;
    private LocalDateTime dateTime;
    private Type type;
    private Stop stop;
    private Company company;
    private String busId;
    private String primaryAccountNumber;

}
