package com.sec.secwatch.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rbitt on 2017-04-27.
 */

public class UserPinNumber {

    @JsonProperty("nick")
    private String id;

    @JsonProperty("pin")
    private String pin;

    public String getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }
}
