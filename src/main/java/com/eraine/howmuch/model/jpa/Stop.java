package com.eraine.howmuch.model.jpa;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Stop {

    @Id
    @Column(name = "stop_id")
    private String id;

    @Column(name = "stop_name")
    private String name;

    private LocalDateTime created;
    private LocalDateTime updated;

    public Stop(String id) {
        this.id = id;
    }

}
