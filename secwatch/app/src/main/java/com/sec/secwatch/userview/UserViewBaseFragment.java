package com.sec.secwatch.userview;

import android.support.v4.app.Fragment;

import com.sec.secwatch.userview.UserViewPagerAdapter.SummaryChangeListener;
import com.sec.secwatch.wrapper.UserInfoAllWrapper;

public class UserViewBaseFragment extends Fragment implements SummaryChangeListener {

    protected UserInfoAllWrapper mUserInfo = null;


    @Override
    public void onSummaryChanged(int summary) {

    }

    public void setUserInfo(UserInfoAllWrapper userInfo) {
        mUserInfo = userInfo;
    }
}
