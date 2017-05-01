package com.sec.secwatch.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rbitt on 2017-04-30.
 */

public class GameInfoHeroDetail {

    @JsonProperty("time_played")
    private int time_played;

    public int getTime_played() {
        return time_played;
    }

}
