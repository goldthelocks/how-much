package com.eraine.howmuch.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Company {

    private String id;
    private String name;

    public Company(String id) {
        this.id = id;
    }

}
