package com.eraine.howmuch.model.jpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Trip {

    public enum Status {
        COMPLETED, INCOMPLETE, CANCELLED
    }

    private Long id;
    private LocalDateTime started;
    private LocalDateTime finished;
    private Long durationInSeconds;
    private Stop fromStop;
    private Stop toStop;
    private BigDecimal amount;
    private String currencyCode;
    private Company company;
    private String busId;
    private String primaryAccountNumber;
    private Status status;

}
