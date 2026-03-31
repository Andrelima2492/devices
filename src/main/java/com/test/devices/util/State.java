package com.test.devices.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.test.devices.exceptions.InvalidStateException;

import java.util.Arrays;

public enum State {
    AVAILABLE,
    IN_USE,
    INACTIVE;
    
    @JsonValue
    public String toJson(){
        return this.name().toLowerCase().replace("_","-");
    }

    @JsonCreator
    public static State fromString(String value){
        return Arrays.stream(State.values())
                .filter(s->s.name().equalsIgnoreCase(value.replace("-","_")))
                .findFirst().orElseThrow(()->
                        new InvalidStateException(value + " is not a valid state"));
    }
    
}
