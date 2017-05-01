package com.sec.secwatch.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.sec.secwatch.AnimationUtil;
import com.sec.secwatch.R;
import com.sec.secwatch.api.getUserStaticsByName;
import com.sec.secwatch.api.getUserStaticsRange;
import com.sec.secwatch.userview.UserViewActivity;
import com.sec.secwatch.wrapper.UserInfoWrapper;
import com.sec.widget.Loading.rotate.RotateLoading;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rbitt on 2017-04-30.
 */

public class FavoriteFragment extends SearchableFragment implements SwipyRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    private SwipyRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mStaticsRecyclerView;

    private StaticsRecyclerViewAdapter mStaticsAdapter;

    private ArrayList<UserInfoWrapper> mUserArray;

    private final static int RECYCLERVIEW_FIRST_LOADING_PAGE_SIZE = 10;

    private final static int RECYCLERVIEW_PAGE_SIZE = 20;

    private final static int STATE_INIT = 0;

    private final static int STATE_IDLE = 1;

    private int mState = STATE_INIT;

    private View mToastView = null;

    private View mCoverView = null;

    private RotateLoading mLoading = null;

    private String mSearchString = "";

    private AsyncHttpResponseHandler mResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                getUserStaticsRange.UserStaticsRangeWrapper wrapper = mapper.readValue(responseBody, getUserStaticsRange.UserStaticsRangeWrapper.class);
                int start = mUserArray.size();
                mUserArray.addAll( wrapper.getProfiles() );
                int end = mUserArray.size();
                mStaticsAdapter.notifyItemRangeInserted( start, end );
                mSwipeRefreshLayout.setRefreshing(false);

                if( mState == STATE_INIT ) {
                    AnimationUtil.fadeOut(mCoverView,430);
                    mState = STATE_IDLE;
                    mLoading.stop();
                }

                if( mStaticsAdapter.getItemCount() == 0 ) {
                    AnimationUtil.fadeIn(mToastView, 330);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            mSwipeRefreshLayout.setRefreshing(false);

            if( mStaticsAdapter.getItemCount() == 0 ) {
                AnimationUtil.fadeIn(mToastView, 330);
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statics_fragment, null);
        mSwipeRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.statics_swipe_refresh);
        mStaticsRecyclerView = (RecyclerView) view.findViewById(R.id.statics_recycler_view);
        mCoverView = view.findViewById(R.id.statics_cover_container);
        mLoading = (RotateLoading) view.findViewById(R.id.statics_cover_loading);
        mLoading.start();

        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setRefreshing(true);

        // use a linear layout manager
        mStaticsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUserArray = new ArrayList<>();

        mToastView = view.findViewById(R.id.statics_toast_container);
        mToastView.setVisibility(View.GONE);

        mStaticsAdapter = new StaticsRecyclerViewAdapter( mUserArray );
        mStaticsAdapter.setRecyclerViewClickListener(this);
        mStaticsRecyclerView.setAdapter( mStaticsAdapter );

        getUserStaticsByName.get(mUserArray.size(), RECYCLERVIEW_FIRST_LOADING_PAGE_SIZE, "", mResponseHandler);

        return view;
    }

    @Override
    public void setSearchText(String searchText) {

        mSearchString = searchText;
        mToastView.setVisibility(View.GONE);

        int end = mUserArray.size();
        mUserArray.clear();
        mStaticsAdapter.notifyItemRangeRemoved( 0, end );
        getUserStaticsRange.get(0, RECYCLERVIEW_FIRST_LOADING_PAGE_SIZE, mSearchString, mResponseHandler);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {

        mToastView.setVisibility(View.GONE);

        switch ( swipyRefreshLayoutDirection ) {
            case TOP:
                AnimationUtil.fadeIn(mCoverView,230);
                mState = STATE_INIT;
                mLoading.start();
                int end = mUserArray.size();
                mUserArray.clear();
                mStaticsAdapter.notifyItemRangeRemoved( 0, end );
                getUserStaticsByName.get(0,end,"",mResponseHandler);
                break;
            case BOTTOM:
                getUserStaticsByName.get(mUserArray.size(), RECYCLERVIEW_PAGE_SIZE, "", mResponseHandler);
                break;
        }
    }

    /***
     * 리사이클러 뷰 호버뷰 팝업시 아이템 클릭 시
     * @param view
     */
    @Override
    public void onHoverClick(View view) {

    }

    /***
     * 사용자정보 자세히 보기 클릭 시
     * @param userID
     */
    @Override
    public void onUserStaticsViewClick(String userID) {
        Intent intent = new Intent();
        intent.putExtra(UserViewActivity.USER_VIEW_STATICS_ID, userID);
        intent.setClass(getContext(), UserViewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
    }

}
