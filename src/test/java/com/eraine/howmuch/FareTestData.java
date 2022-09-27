package com.eraine.howmuch;

import com.eraine.howmuch.model.jpa.Fare;
import com.eraine.howmuch.model.jpa.Stop;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public final class FareTestData {

    public static List<Fare> getFares() {
        Stop stop1 = new Stop("Stop1");
        Stop stop2 = new Stop("Stop2");
        Stop stop3 = new Stop("Stop3");

        Fare fare1 = new Fare();
        fare1.setFromStop(stop1);
        fare1.setToStop(stop2);
        fare1.setAmount(new BigDecimal("3.25"));

        Fare fare2 = new Fare();
        fare2.setFromStop(stop2);
        fare2.setToStop(stop1);
        fare2.setAmount(new BigDecimal("3.25"));

        Fare fare3 = new Fare();
        fare3.setFromStop(stop2);
        fare3.setToStop(stop3);
        fare3.setAmount(new BigDecimal("5.50"));

        Fare fare4 = new Fare();
        fare4.setFromStop(stop3);
        fare4.setToStop(stop2);
        fare4.setAmount(new BigDecimal("5.50"));

        Fare fare5 = new Fare();
        fare5.setFromStop(stop1);
        fare5.setToStop(stop3);
        fare5.setAmount(new BigDecimal("7.30"));

        Fare fare6 = new Fare();
        fare6.setFromStop(stop3);
        fare6.setToStop(stop1);
        fare6.setAmount(new BigDecimal("7.30"));

        return Arrays.asList(fare1, fare2, fare3, fare4, fare5, fare6);
    }

}
