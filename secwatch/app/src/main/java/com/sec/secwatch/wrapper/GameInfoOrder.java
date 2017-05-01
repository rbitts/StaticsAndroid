package com.sec.secwatch.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by rbitt on 2017-04-30.
 */

public class GameInfoOrder {

    @JsonProperty("name")
    private String name;

    @JsonProperty("time_played")
    private int time_played;

    public String getName() {
        return name;
    }

    public int getTime_played() {
        return time_played;
    }
}
