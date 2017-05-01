package com.sec.secwatch;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.secwatch.wrapper.UserInfoAllWrapper;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by rbitt on 2017-04-28.
 */

public class UserObservable extends Observable {

    private UserInfoAllWrapper mProfile = null;

    public void setUserProfile( byte[] jsonBytes ) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            mProfile = mapper.readValue(jsonBytes, UserInfoAllWrapper.class);
            setChanged();
            notifyObservers(mProfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserInfoAllWrapper registerObserver(Observer observer) {
        this.addObserver(observer);
        return mProfile;
    }

    public UserInfoAllWrapper getUserInfo() {
        return mProfile;
    }
}
