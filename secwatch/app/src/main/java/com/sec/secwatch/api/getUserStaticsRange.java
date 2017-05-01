package com.sec.secwatch.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sec.secwatch.wrapper.UserInfoWrapper;

import java.util.ArrayList;

/**
 * Created by rbitt on 2017-04-26.
 */

public class getUserStaticsRange {

    public final static String getUserStaticsRangeURL = "http://rbits.xyz:3000/api/getUserStaticsRange";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(int start, int length, String search, AsyncHttpResponseHandler responseHandler ) {

        RequestParams params = new RequestParams();
        params.put("start", start);
        params.put("length", length);
        params.put("draw", 1);
        params.put("search", search);

        client.get(getUserStaticsRangeURL, params, responseHandler);
    }

    public static class UserStaticsRangeWrapper {

        @JsonProperty("data")
        private ArrayList<UserInfoWrapper> profiles;

        public ArrayList<UserInfoWrapper> getProfiles() {
            return profiles;
        }
    }
}
