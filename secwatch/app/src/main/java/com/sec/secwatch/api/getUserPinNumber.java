package com.sec.secwatch.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by rbitt on 2017-04-27.
 */

public class getUserPinNumber {

    public final static String getUserPinNumberURL = "http://rbits.xyz:3000/api/getUserPin";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String id, AsyncHttpResponseHandler responseHandler ) {

        RequestParams params = new RequestParams();
        params.put("nick", id);

        client.get(getUserPinNumberURL, params, responseHandler);

    }
}
