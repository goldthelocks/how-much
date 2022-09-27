package com.eraine.howmuch.repository;

import com.eraine.howmuch.model.jpa.Fare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FareRepository extends JpaRepository<Fare, Long> {

}
