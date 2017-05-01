package com.sec.secwatch.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by rbitt on 2017-04-30.
 */

public class GameInfo {

    @JsonProperty("data")
    private ArrayList<GameInfoHeroData> data;

    @JsonProperty("order")
    private ArrayList<GameInfoOrder> order;

    public ArrayList<GameInfoHeroData> getData() {
        return data;
    }

    public ArrayList<GameInfoOrder> getOrder() {
        return order;
    }

}
