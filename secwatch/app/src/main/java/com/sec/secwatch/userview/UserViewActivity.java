package com.sec.secwatch.userview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sec.secwatch.App;
import com.sec.secwatch.R;
import com.sec.secwatch.api.getUserStatics;
import com.sec.secwatch.wrapper.UserInfoAllWrapper;
import com.sec.widget.NavigationTabStrip.NavigationTabStrip;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

import static com.sec.secwatch.userview.UserViewPagerAdapter.SUMMARY_TYPE_COMPETITIVE;

/**
 * Created by rbitt on 2017-04-29.
 */

public class UserViewActivity extends AppCompatActivity implements UserViewPagerAdapter.SummaryChangeListener{

    public static final String USER_VIEW_STATICS_ID = "USER_ID";

    private Context mContext = null;

    private UserInfoAllWrapper mUserProfile = null;

    private ImageView mPortraitView = null;

    private ViewPager mViewPager = null;

    private NavigationTabStrip mNavigationTabStrip = null;

    private AsyncHttpResponseHandler mUserResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                mUserProfile = mapper.readValue(responseBody, UserInfoAllWrapper.class);
                configureUserStaticsView(mUserProfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        this.setContentView(R.layout.activity_user_view);

        Intent intent = getIntent();
        String userID = intent.getStringExtra(USER_VIEW_STATICS_ID);
        Log.d(App.TAG, String.format("[ %s ] %s",this.getClass().getSimpleName() , userID));

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(userID);

        Glide
                .with(mContext)
                .load(R.drawable.portrait_background)
                .centerCrop()
                .crossFade()
                .into(new ViewTarget<View, GlideDrawable>(findViewById(R.id.user_view_portrait_background)) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource);
                    }
                });

        mPortraitView = (ImageView) findViewById(R.id.user_view_portrait);

        mViewPager = (ViewPager) findViewById(R.id.user_view_view_pager);

        mNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.user_view_navigation_tab_strip);
        mNavigationTabStrip.setTitles("주요통계", "영웅", "통계");

        getUserStatics.get(userID, mUserResponseHandler);


    }

    public void configureUserStaticsView(UserInfoAllWrapper info){

        // 초상화
        Glide
                .with(mContext)
                .load(Uri.parse(info.getPortrait()))
                .centerCrop()
                .crossFade()
                .into(mPortraitView);

        UserViewPagerAdapter userViewPagerAdapter = new UserViewPagerAdapter(getSupportFragmentManager(), mUserProfile);
        userViewPagerAdapter.addSummaryChangeListener(this);

        mViewPager.setAdapter(userViewPagerAdapter);

        mNavigationTabStrip.setViewPager(mViewPager, 0);
        mNavigationTabStrip.setTabIndex(0, true);
    }

    @Override
    public void onSummaryChanged(int summary) {

        Glide
                .with(mContext)
                .load(Uri.parse(summary == SUMMARY_TYPE_COMPETITIVE ? mUserProfile.getCompetitive_portrait() : mUserProfile.getQuickplay_portrait()))
                .centerCrop()
                .crossFade()
                .into(mPortraitView);

    }
}
