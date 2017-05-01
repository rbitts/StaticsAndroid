package com.sec.secwatch.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rbitt on 2017-04-30.
 */

public class GameInfoHeroData {

    @JsonProperty("display_name")
    private String display_name;

    @JsonProperty("thumb")
    private String thumb;

    @JsonProperty("portrait")
    private String portrait;

    @JsonProperty("detail")
    private GameInfoHeroDetail detail;

    public String getDisplay_name() {
        return display_name;
    }

    public String getThumb() {
        return thumb;
    }

    public String getPortrait() {
        return portrait;
    }

    public GameInfoHeroDetail getDetail() {
        return detail;
    }
}
