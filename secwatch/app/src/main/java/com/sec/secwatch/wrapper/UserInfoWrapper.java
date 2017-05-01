package com.sec.secwatch.wrapper;

import android.net.Uri;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rbitt on 2017-04-30.
 */

public class UserInfoWrapper {

    public static final int USER_TYPE_NORMAL = 0;

    public static final int USER_TYPE_TEAM_LEADER = 1;

    public static final int USER_TYPE_LEADER = 2;

    public static final int USER_TYPE_SUPERVISOR = 3;


    @JsonProperty("nick")
    protected String id;

    @JsonProperty("pin")
    protected String pin;

    @JsonProperty("role")
    protected int role;

    @JsonProperty("profile")
    protected Profile profile;

    @JsonProperty("global")
    protected Global global;

    @JsonProperty("competitive_global")
    protected Global competitive_global;

    @JsonProperty("quickplay_global")
    protected Global quickplay_global;

    @JsonProperty("timestamp")
    protected String timestamp;

    @JsonProperty("competitive_portrait")
    protected String competitive_portrait;

    @JsonProperty("quickplay_portrait")
    protected String quickplay_portrait;

    @JsonProperty("portrait")
    protected String portrait;


    @JsonProperty("hasCompetitive")
    protected boolean hasCompetitive;


    public String getId() {
        return id.replace("-","#");
    }

    public Profile getProfile() {
        return profile;
    }

    public Global getGlobal() {
        return global;
    }

    public Global getCompetitive_global() {
        return competitive_global;
    }

    public Global getQuickplay_global() {
        return quickplay_global;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPortrait() {
        return portrait.replace("/img/portrait/" , "file:///android_asset/large/");
    }

    public String getCompetitive_portrait() {
        return competitive_portrait.replace("/img/portrait/" , "file:///android_asset/large/");
    }

    public String getQuickplay_portrait() {
        return quickplay_portrait.replace("/img/portrait/" , "file:///android_asset/large/");
    }

    public String getPin() {
        return pin;
    }

    public int getRole() {
        return role;
    }

    public boolean hasCompetitive() {
        return hasCompetitive;
    }
}
