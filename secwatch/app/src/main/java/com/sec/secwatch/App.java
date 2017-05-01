package com.sec.secwatch;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sec.secwatch.api.getUserStatics;
import com.sec.secwatch.api.updateUserPin;
import com.sec.secwatch.wrapper.FavoriteWrapper;
import com.sec.secwatch.wrapper.UserInfoAllWrapper;

import java.io.IOException;
import java.util.Observer;

import cz.msebera.android.httpclient.Header;


/**
 * Created by rbitt on 2017-04-28.
 */

public class App extends Application implements GlideModule {

    public static final String TAG = "SECWATCH";

    public static final String SEC_WATCH_PREFERENCE = "secwatch_preference";

    public static final String SEC_WATCH_USER_PREFERENCE = "secwatch_user_preference";

    public static final String SEC_WATCH_FAVORITE_PREFERENCE = "secwatch_favorite_preference";

    public static final String SEC_WATCH_COMPARE_VIEW_PREFERENCE = "secwatch_compare_preference";

    private static UserObservable mCurrentUser = null;

    private static Context mContext = null;

    private static  FavoriteWrapper mFavorites;

    private static AsyncHttpResponseHandler mCurrentUserHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            mCurrentUser.setUserProfile(responseBody);
            saveCurrentUserInfo();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };

    public static Context getContext() { return mContext; }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        mCurrentUser = new UserObservable();

        SharedPreferences prefs = getSharedPreferences(SEC_WATCH_PREFERENCE, MODE_PRIVATE);
        String mCurrentUserID = prefs.getString(SEC_WATCH_USER_PREFERENCE,  null);
        if( mCurrentUserID != null && !mCurrentUserID.isEmpty()) {
            requestCurrentUserInfo(mCurrentUserID);
        }

        String favorites = prefs.getString(SEC_WATCH_FAVORITE_PREFERENCE,  null);
        if ( favorites == null ) {
            mFavorites = new FavoriteWrapper();
        } else {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                mFavorites = mapper.readValue(favorites, FavoriteWrapper.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int cacheSize = maxMemory / 8;
    private final int DISK_CACHE_SIZE = 1024 * 1024 * 10;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", DISK_CACHE_SIZE))
                .setMemoryCache(new LruResourceCache(cacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
    /***
     *
     *  현재 사용자 정보 변경 요청
     *
     * @param userID
     */
    public static void requestCurrentUserInfo( String userID ) {
        getUserStatics.get(userID, mCurrentUserHandler);
    }

    public static void setCurrentUserInfo( String id, String pin ) {
        updateUserPin.get(id, pin, mCurrentUserHandler );
    }

    public static UserInfoAllWrapper registerCurrentUserObserver(Observer observer) {
        return mCurrentUser.registerObserver(observer);
    }

    /***
     *
     *  현재 사용자 정보를 가져온다.
     *
     * @return 현재 사용자
     */
    public static UserInfoAllWrapper getCurrentUserInfo() {
        return mCurrentUser.getUserInfo();
    }

    private static void saveCurrentUserInfo() {
        SharedPreferences prefs = mContext.getSharedPreferences(SEC_WATCH_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor= prefs.edit();
        editor.putString(SEC_WATCH_USER_PREFERENCE, getCurrentUserInfo().getId());
        editor.commit();
    }

    /***
     * 즐겨찾기에 추가한다.
     * @param id 사용자 아이디 ( 아이디#배틀태그 )
     * @param enable 즐겨찾기 on off
     */
    public static void setFavorite(String id , boolean enable) {
        if( enable )
            mFavorites.add(id);
        else
            mFavorites.remove(id);
        saveFavorite();
    }

    /***
     * 즐겨찾기가 되어있는지 확인한다.
     * @param id 아이디
     * @return 즐겨찾기
     */
    public static boolean isFavorite(String id ) {
        return mFavorites.isActive(id);
    }

    private static void saveFavorite() {
        SharedPreferences prefs = mContext.getSharedPreferences(SEC_WATCH_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor= prefs.edit();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String value = mapper.writeValueAsString(mFavorites);
            editor.putString(SEC_WATCH_FAVORITE_PREFERENCE, value);
            editor.commit();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /***
     *
     * @return 현재 즐겨찾기된 사용자 정보를 가져온다.
     */
    public static String getFavoriteUser() {
        return mFavorites.toString();
    }


    public static boolean getIsCompareState() {
        SharedPreferences prefs = mContext.getSharedPreferences(SEC_WATCH_PREFERENCE, MODE_PRIVATE);
        boolean isCompare = prefs.getBoolean(SEC_WATCH_COMPARE_VIEW_PREFERENCE, false );
        return isCompare;
    }

    public static void setCompareState(boolean isCompare) {
        SharedPreferences prefs = mContext.getSharedPreferences(SEC_WATCH_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SEC_WATCH_COMPARE_VIEW_PREFERENCE, isCompare );
        editor.commit();
    }

}
