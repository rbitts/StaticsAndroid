package com.sec.secwatch.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rbitt on 2017-04-25.
 */

public class UserInfoAllWrapper extends UserInfoWrapper {


    @JsonProperty("competitive")
    private GameInfo competitive;

    @JsonProperty("quickplay")
    private GameInfo quickplay;

    public GameInfo getCompetitive() {
        return competitive;
    }

    public GameInfo getQuickplay() {
        return quickplay;
    }


}
