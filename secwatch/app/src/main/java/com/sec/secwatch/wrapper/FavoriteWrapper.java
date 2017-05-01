package com.sec.secwatch.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by rbitt on 2017-04-28.
 */

public class FavoriteWrapper {

    @JsonProperty("ids")
    ArrayList<String> favorites;

    public FavoriteWrapper() {
        favorites = new ArrayList<>();
    }

    public void add(String id) {
        if( !favorites.contains(id) ) {
            favorites.add(id);
        }
    }

    public void remove(String id) {
        if( favorites.contains(id) ){
            favorites.remove(id);
        }
    }

    public boolean isActive(String id) {
        return favorites.contains(id);
    }

    public String toString() {
        if( favorites.size() < 1 )
            return "";

        String str = "";

        for( String id : favorites ) {
            str += id + ",";
        }
        return str.substring(0, str.length() -1);
    }

}
