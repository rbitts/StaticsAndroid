package com.sec.secwatch.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import static com.sec.secwatch.api.getUserStatics.getUserStaticsURL;

/**
 * Created by rbitt on 2017-04-28.
 */

public class updateUserPin {
    public final static String updateUserPinURL = "http://rbits.xyz:3000/api/updateUserPin";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String id, String pin,  AsyncHttpResponseHandler responseHandler ) {

        RequestParams params = new RequestParams();
        params.put("nick", id);
        params.put("pin", pin);

        client.get(updateUserPinURL, params, responseHandler);

    }
}
