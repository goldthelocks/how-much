package com.eraine.howmuch.model.api;

import com.eraine.howmuch.model.jpa.Tap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TapPair {

    private Tap tapOn;
    private Tap tapOff;

}
