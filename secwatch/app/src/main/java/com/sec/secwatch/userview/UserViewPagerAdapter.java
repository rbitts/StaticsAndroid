package com.sec.secwatch.userview;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sec.secwatch.wrapper.UserInfoAllWrapper;

import java.util.ArrayList;

/**
 * Created by rbitt on 2017-04-29.
 */

public class UserViewPagerAdapter extends FragmentPagerAdapter {

    private int MAX_PAGE = 3;

    private UserInfoAllWrapper mUserInfo;

    public static final int SUMMARY_TYPE_COMPETITIVE = 0;
    public static final int SUMMARY_TYPE_QUICKPLAY = 1;

    public interface SummaryChangeListener {
        void onSummaryChanged (int summary);
    }

    private ArrayList<SummaryChangeListener> mSummaryChangeListener = new ArrayList<>();

    private SummaryChangeListener mSummaryChangeListenerHandler = new SummaryChangeListener() {
        @Override
        public void onSummaryChanged(int summary) {
            for(SummaryChangeListener listener : mSummaryChangeListener) {
                listener.onSummaryChanged(summary);
            }
        }
    };

    public UserViewPagerAdapter(FragmentManager fm, UserInfoAllWrapper userInfo) {
        super(fm);
        mUserInfo = userInfo;
    }

    @Override
    public Fragment getItem(int position) {
        if(position < 0 || MAX_PAGE <= position)
            return null;

        UserViewBaseFragment fragment = null;

        switch (position) {
            case 0:
                fragment = new UserViewSummaryFragment();
                ((UserViewSummaryFragment)fragment).setSummaryChangeListener(mSummaryChangeListenerHandler);
                addSummaryChangeListener(fragment);
                fragment.setUserInfo(mUserInfo);
                break;
            case 1:
                fragment = new UserViewPlayTimeFragment();
                addSummaryChangeListener(fragment);
                fragment.setUserInfo(mUserInfo);
                break;
            case 2:
                fragment = new UserViewPlayTimeFragment();
//                addSummaryChangeListener(fragment);
                fragment.setUserInfo(mUserInfo);
                break;
        }

        return fragment;
    }


    public void addSummaryChangeListener(SummaryChangeListener listener) {
        if( !mSummaryChangeListener.contains(listener) ) {
            mSummaryChangeListener.add(listener);
        }
    }

    @Override
    public int getCount() {
        return MAX_PAGE;
    }
}
