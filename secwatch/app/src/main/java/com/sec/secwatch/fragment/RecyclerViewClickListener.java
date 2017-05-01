package com.sec.secwatch.fragment;

import android.view.View;

/**
 * Created by rbitt on 2017-04-27.
 */

public interface RecyclerViewClickListener {
    void onHoverClick(View view);
    void onUserStaticsViewClick(String userID);
    void onClick(View view);
}
