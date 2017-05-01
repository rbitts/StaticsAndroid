package com.sec.secwatch.userview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sec.secwatch.R;
import com.sec.secwatch.wrapper.UserInfoAllWrapper;

import static com.sec.secwatch.userview.UserViewPagerAdapter.SUMMARY_TYPE_COMPETITIVE;
import static com.sec.secwatch.userview.UserViewPagerAdapter.SUMMARY_TYPE_QUICKPLAY;

/**
 * Created by rbitt on 2017-04-29.
 */

public class UserViewPlayTimeFragment extends UserViewBaseFragment {

    private RecyclerView mRecyclerView = null;

    private UserViewPlayTimeAdapter mCompetitiveAdapter = null;

    private UserViewPlayTimeAdapter mQuickplayAdapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_view_play_time_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.user_view_time_played_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCompetitiveAdapter = new UserViewPlayTimeAdapter(mUserInfo.getCompetitive().getData());
        mQuickplayAdapter = new UserViewPlayTimeAdapter(mUserInfo.getQuickplay().getData());

        mRecyclerView.setAdapter(mCompetitiveAdapter);
        return view;
    }

    @Override
    public void onSummaryChanged(int summary) {
        switch (summary) {
            case SUMMARY_TYPE_COMPETITIVE:
                mRecyclerView.setAdapter(mCompetitiveAdapter);
                break;
            case SUMMARY_TYPE_QUICKPLAY:
                mRecyclerView.setAdapter(mQuickplayAdapter);
                break;
        }
    }
}
