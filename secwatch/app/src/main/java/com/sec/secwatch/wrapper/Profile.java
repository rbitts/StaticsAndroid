package com.sec.secwatch.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rbitt on 2017-04-25.
 */

public class Profile {

    @JsonProperty("nick")
    private String id;

    @JsonProperty("levelBasePicture")
    private String levelBackground;

    @JsonProperty("level")
    private int level;

    @JsonProperty("levelPicture")
    private String levelPicture;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("rankPicture")
    private String rankPicture;

    @JsonProperty("url")
    private String detailURL;

    public String getId() {
        return id;
    }

    public String getLevelBackground() {
        return levelBackground;
    }

    public int getLevel() {
        return level;
    }

    public String getLevelPicture() {
        return levelPicture;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getRank() {
        return rank;
    }

    public String getRankPicture() {
        return rankPicture;
    }

    public String getDetailURL() {
        return detailURL;
    }
}
