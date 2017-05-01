package com.sec.secwatch.userview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sec.secwatch.App;
import com.sec.secwatch.R;
import com.sec.secwatch.api.updateUserStatics;
import com.sec.secwatch.wrapper.GameInfoConverter;
import com.sec.secwatch.wrapper.UserInfoAllWrapper;
import com.sec.secwatch.wrapper.UserInfoWrapper;
import com.sec.widget.ProgressButton.iml.ActionProcessButton;
import com.sec.widget.SwipeSelector.OnSwipeItemSelectedListener;
import com.sec.widget.SwipeSelector.SwipeItem;
import com.sec.widget.SwipeSelector.SwipeSelector;
import com.sec.secwatch.userview.UserViewPagerAdapter.SummaryChangeListener;

import org.w3c.dom.Text;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

import static com.sec.secwatch.App.getCurrentUserInfo;
import static com.sec.secwatch.userview.UserViewPagerAdapter.SUMMARY_TYPE_COMPETITIVE;
import static com.sec.secwatch.userview.UserViewPagerAdapter.SUMMARY_TYPE_QUICKPLAY;


/**
 * Created by rbitt on 2017-04-30.
 */

public class UserViewSummaryFragment extends UserViewBaseFragment implements CompoundButton.OnCheckedChangeListener  {

    private Context mContext;

    private View mContainer = null;

    private ViewGroup mSingleViewContainer = null;
    private ViewGroup mCompareViewContainer = null;

    private TextView mLeftUserInfoIDView = null;
    private ImageView mLeftBackgroundView = null;

    private TextView mRightUserInfoIDView = null;
    private ImageView mRightBackgroundView = null;

    private TextView mLeftUserInfoRankView = null;
    private TextView mRightUserInfoRankView = null;

    private ImageView mLeftUserInfoRankPictureView = null;
    private ImageView mRightUserInfoRankPictureView = null;

    private Switch mCompareSwitchView = null;

    private SwipeSelector mSummarySelector = null;

    private ActionProcessButton mRefreshButton = null;

    private TextView mTimeStampTextView = null;

    private AsyncHttpResponseHandler mRefreshHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                mUserInfo = mapper.readValue(responseBody, UserInfoAllWrapper.class);
                mRefreshButton.setProgress(100);
                mRefreshButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshButton.setProgress(0);
                        mRefreshButton.setEnabled(true);
                    }
                },2000);


                int SummaryType;
                if( mSummarySelector.getSelectedItem().getValue().equals(getResources().getString(R.string.summary_competitive_value)) ) {
                    SummaryType = SUMMARY_TYPE_COMPETITIVE;
                } else {
                    SummaryType = SUMMARY_TYPE_QUICKPLAY;
                }

                if(mSummaryChangeListener != null) {
                    mSummaryChangeListener.onSummaryChanged(SummaryType);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            mRefreshButton.setProgress(-1);
            mRefreshButton.setEnabled(true);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_view_summary_layout, null);
        mContext = view.getContext();
        mContainer = view;

        mSingleViewContainer = (ViewGroup) view.findViewById(R.id.user_view_summary_single_container);
        mCompareViewContainer = (ViewGroup) view.findViewById(R.id.user_view_summary_compare_container);

        mLeftBackgroundView = (ImageView) view.findViewById(R.id.user_view_summary_left_background);
        mLeftUserInfoIDView = (TextView) view.findViewById(R.id.user_view_summary_left_id);
        mLeftUserInfoRankPictureView = (ImageView) view.findViewById(R.id.user_view_compare_left_rank_picture);

        mRightBackgroundView = (ImageView) view.findViewById(R.id.user_view_summary_right_background);
        mRightUserInfoIDView = (TextView) view.findViewById(R.id.user_view_summary_right_id);
        mRightUserInfoRankPictureView = (ImageView) view.findViewById(R.id.user_view_compare_right_rank_picture);

        mLeftUserInfoRankView = (TextView) view.findViewById(R.id.user_view_compare_left_rank);
        mRightUserInfoRankView = (TextView) view.findViewById(R.id.user_view_compare_right_rank);

        mTimeStampTextView = (TextView) view.findViewById(R.id.user_view_summary_timestamp);
        mCompareSwitchView = (Switch) view.findViewById(R.id.user_view_summary_compare_switch);

        mRefreshButton = (ActionProcessButton) view.findViewById(R.id.user_view_summary_refresh_button);
        mRefreshButton.setMode(ActionProcessButton.Mode.ENDLESS);

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefreshButton.setEnabled(false);
                mRefreshButton.setProgress(10);
                updateUserStatics.get(mUserInfo.getId(), mRefreshHandler);
            }
        });

        mSummarySelector = (SwipeSelector) view.findViewById(R.id.user_view_summary_type_selector);
        mSummarySelector.selectItemAt( mUserInfo.hasCompetitive() ? 0 : 1);
        mSummarySelector.setOnItemSelectedListener(new OnSwipeItemSelectedListener() {

            private boolean blockEvent = false;

            @Override
            public void onItemSelected(SwipeItem item) {
                if( item.getValue().equals(getResources().getString(R.string.summary_competitive_value))) {

                    if( !mUserInfo.hasCompetitive() ) {
                        blockEvent = true;
                        mSummarySelector.selectItemAt(1, true);
                        return;
                    }

                    if(mSummaryChangeListener != null) {
                        mSummaryChangeListener.onSummaryChanged(SUMMARY_TYPE_COMPETITIVE);
                    }
                }
                else if( item.getValue().equals(getResources().getString(R.string.summary_quickplay_value)) ) {
                    if( blockEvent ) {
                        blockEvent = false;
                        return;
                    }

                    if(mSummaryChangeListener != null) {
                        mSummaryChangeListener.onSummaryChanged(SUMMARY_TYPE_QUICKPLAY);
                    }
                }
            }
        });

        UserInfoAllWrapper myInfo = getCurrentUserInfo();

        if( myInfo == null ) {
            mCompareSwitchView.setChecked(false);
            showContentView(false);
        } else {
            boolean isCompare = App.getIsCompareState();
            mCompareSwitchView.setChecked(isCompare);
            showContentView(isCompare);
        }

        mCompareSwitchView.setOnCheckedChangeListener( this );

        return view;
    }

    private void configureSingleView(View view) {

        ImageView levelBackground = (ImageView) view.findViewById(R.id.user_view_summary_level_background);
        Glide
                .with(mContext)
                .load(Uri.parse(mUserInfo.getProfile().getLevelBackground()))
                .crossFade()
                .into(levelBackground);

        ImageView levelStar = (ImageView) view.findViewById(R.id.user_view_summary_level_star);
        Glide
                .with(mContext)
                .load(Uri.parse(mUserInfo.getProfile().getLevelPicture()))
                .crossFade()
                .into(levelStar);

        TextView levelView = (TextView) view.findViewById(R.id.user_view_summary_level);
        levelView.setText(String.valueOf(mUserInfo.getProfile().getLevel()));

        if( mUserInfo.hasCompetitive() ) {

            ImageView rankPicture = (ImageView) view.findViewById(R.id.user_view_summary_rank_picture);

            Glide
                    .with(mContext)
                    .load(Uri.parse(mUserInfo.getProfile().getRankPicture()))
                    .crossFade()
                    .into(rankPicture);

            TextView rankView = (TextView) view.findViewById(R.id.user_view_summary_rank);
            rankView.setText(String.valueOf(mUserInfo.getProfile().getRank()));

        }

        TextView wonGameView = (TextView) view.findViewById(R.id.user_view_summary_won_game);
        wonGameView.setText(String.format("승리한 게임 %d회", mUserInfo.getGlobal().getGames_won() ));

//            ImageView avatarView = (ImageView) view.findViewById(R.id.user_view_summary_avatar);
//            Glide
//                    .with(mContext)
//                    .load(Uri.parse(mUserInfo.getProfile().getAvatar()))
//                    .crossFade()
//                    .into(avatarView);

        //mSingleViewContainer
        UserViewSummarySingleView winRateChart = (UserViewSummarySingleView) view.findViewById(R.id.user_view_summary_single_win_rate);
        winRateChart.setContent("승률", "%" , 100, (float)mUserInfo.getGlobal().getGames_won() / (float)mUserInfo.getGlobal().getGames_played() * 100f);

        UserViewSummarySingleView kdaChart = (UserViewSummarySingleView) view.findViewById(R.id.user_view_summary_single_kda);
        float kda = (float)mUserInfo.getGlobal().getEliminations() / (float)mUserInfo.getGlobal().getDeaths();
        kdaChart.setContent("목숨 당 처치", "", (int) (Math.ceil(kda / 5.0f) * 5), kda);

        UserViewSummarySingleView damageChart = (UserViewSummarySingleView) view.findViewById(R.id.user_view_summary_single_damage_done);
        float damage = mUserInfo.getGlobal().getDamage_done_average();
        damageChart.setContent("준피해-평균", "", (int) (Math.ceil(damage / 100.0f) * 100), damage);

        UserViewSummarySingleView healChart = (UserViewSummarySingleView) view.findViewById(R.id.user_view_summary_single_heal_done);
        float heal = mUserInfo.getGlobal().getHealing_done_average();
        healChart.setContent("치유-평균", "", (int) (Math.ceil(heal / 100.0f) * 100), heal);

    }

    private void configureCompareView(View view) {
        UserInfoAllWrapper compareInfo = App.getCurrentUserInfo();
        if(compareInfo == null) return;

        String v = mSummarySelector.getSelectedItem().getValue();

        // 경쟁전 점수 비교
        if( mSummarySelector.getSelectedItem().getValue().equals(getResources().getString(R.string.summary_competitive_value)) ) {

            // 초상화
            if( mUserInfo.hasCompetitive() ) {

                Glide
                        .with(mContext)
                        .load(Uri.parse(mUserInfo.getCompetitive_portrait()))
                        .crossFade()
                        .into(mLeftBackgroundView);

                Glide
                        .with(mContext)
                        .load(mUserInfo.getProfile().getRankPicture())
                        .crossFade()
                        .centerCrop()
                        .into(mLeftUserInfoRankPictureView);

                mLeftUserInfoIDView.setText(mUserInfo.getId());
                mLeftUserInfoRankView.setText(String.valueOf(mUserInfo.getProfile().getRank()));


                // 초상화

                UserInfoWrapper mCompareUserInfo = App.getCurrentUserInfo();
                if( mCompareUserInfo.hasCompetitive() ) {

                    Glide
                        .with(mContext)
                        .load(Uri.parse(mCompareUserInfo.getCompetitive_portrait()))
                        .crossFade()
                        .into(mRightBackgroundView);


                    Glide
                            .with(mContext)
                            .load(mCompareUserInfo.getProfile().getRankPicture())
                            .crossFade()
                            .centerCrop()
                            .into(mRightUserInfoRankPictureView);

                    mRightUserInfoIDView.setText(mCompareUserInfo.getId());
                    mRightUserInfoRankView.setText(String.valueOf(mCompareUserInfo.getProfile().getRank()));

                }
                // 주요통계 비교
                UserViewSummaryCompareView winRateChart = (UserViewSummaryCompareView) view.findViewById(R.id.user_view_summary_compare_win_rate);
                winRateChart.setContent("승률", "%" , 100, (float)mUserInfo.getCompetitive_global().getGames_won() / (float)mUserInfo.getCompetitive_global().getGames_played() * 100f,
                        (float)compareInfo.getCompetitive_global().getGames_won() / (float)compareInfo.getCompetitive_global().getGames_played() * 100f);

                UserViewSummaryCompareView kdaChart = (UserViewSummaryCompareView) view.findViewById(R.id.user_view_summary_compare_kda);
                float kda = (float)mUserInfo.getCompetitive_global().getEliminations() / (float)mUserInfo.getCompetitive_global().getDeaths();
                float compareKda = (float)compareInfo.getCompetitive_global().getEliminations() / (float)compareInfo.getCompetitive_global().getDeaths();
                kdaChart.setContent("목숨 당 처치", "", (int) Math.max( (Math.ceil(kda / 5.0f) * 5),(Math.ceil(compareKda / 5.0f) * 5)), kda, compareKda);

                UserViewSummaryCompareView damageChart = (UserViewSummaryCompareView) view.findViewById(R.id.user_view_summary_compare_damage_done);
                float damage = mUserInfo.getCompetitive_global().getDamage_done_average();
                float compareDamage = compareInfo.getCompetitive_global().getDamage_done_average();
                damageChart.setContent("준피해-평균", "", (int) Math.max((Math.ceil(damage / 100.0f) * 100),(Math.ceil(compareDamage / 100.0f) * 100)), damage,compareDamage);

                UserViewSummaryCompareView healChart = (UserViewSummaryCompareView) view.findViewById(R.id.user_view_summary_compare_heal_done);
                float heal = mUserInfo.getCompetitive_global().getHealing_done_average();
                float compareHeal = compareInfo.getCompetitive_global().getHealing_done_average();
                healChart.setContent("치유-평균", "", (int) Math.max((Math.ceil(heal / 100.0f) * 100),(Math.ceil(compareHeal / 100.0f) * 100)), heal, compareHeal);

            }

        }
        else if(mSummarySelector.getSelectedItem().getValue().equals(getResources().getString(R.string.summary_quickplay_value))) {

            Glide
                    .with(mContext)
                    .load(Uri.parse(mUserInfo.getQuickplay_portrait()))
                    .crossFade()
                    .into(mLeftBackgroundView);

//            Glide
//                    .with(mContext)
//                    .load(mUserInfo.getProfile().getRankPicture())
//                    .crossFade()
//                    .centerCrop()
//                    .into(mLeftUserInfoRankPictureView);

            mLeftUserInfoIDView.setText(mUserInfo.getId());
//            mLeftUserInfoRankView.setText(String.valueOf(mUserInfo.getProfile().getRank()));


            // 초상화

            UserInfoWrapper mCompareUserInfo = App.getCurrentUserInfo();
            Glide
                    .with(mContext)
                    .load(Uri.parse(mCompareUserInfo.getQuickplay_portrait()))
                    .crossFade()
                    .into(mRightBackgroundView);


//            Glide
//                    .with(mContext)
//                    .load(mCompareUserInfo.getProfile().getRankPicture())
//                    .crossFade()
//                    .centerCrop()
//                    .into(mRightUserInfoRankPictureView);

            mRightUserInfoIDView.setText(mCompareUserInfo.getId());
//            mRightUserInfoRankView.setText(String.valueOf(mCompareUserInfo.getProfile().getRank()));

            // 주요통계 비교
            UserViewSummaryCompareView winRateChart = (UserViewSummaryCompareView) view.findViewById(R.id.user_view_summary_compare_win_rate);
            float gamewon = mUserInfo.getQuickplay_global().getGames_won();
            float compareGamewon = compareInfo.getQuickplay_global().getGames_won();
            winRateChart.setContent("이긴게임", "승" , (int) Math.max( (Math.ceil(gamewon / 100.0f) * 100),(Math.ceil(compareGamewon / 100.0f) * 100)),  gamewon,  compareGamewon);

            UserViewSummaryCompareView kdaChart = (UserViewSummaryCompareView) view.findViewById(R.id.user_view_summary_compare_kda);
            float kda = (float)mUserInfo.getQuickplay_global().getEliminations() / (float)mUserInfo.getQuickplay_global().getDeaths();
            float compareKda = (float)compareInfo.getQuickplay_global().getEliminations() / (float)compareInfo.getQuickplay_global().getDeaths();
            kdaChart.setContent("목숨 당 처치", "", (int) Math.max( (Math.ceil(kda / 5.0f) * 5),(Math.ceil(compareKda / 5.0f) * 5)), kda, compareKda);

            UserViewSummaryCompareView damageChart = (UserViewSummaryCompareView) view.findViewById(R.id.user_view_summary_compare_damage_done);
            float damage = mUserInfo.getQuickplay_global().getDamage_done_average();
            float compareDamage = compareInfo.getQuickplay_global().getDamage_done_average();
            damageChart.setContent("준피해-평균", "", (int) Math.max((Math.ceil(damage / 100.0f) * 100),(Math.ceil(compareDamage / 100.0f) * 100)), damage,compareDamage);

            UserViewSummaryCompareView healChart = (UserViewSummaryCompareView) view.findViewById(R.id.user_view_summary_compare_heal_done);
            float heal = mUserInfo.getQuickplay_global().getHealing_done_average();
            float compareHeal = compareInfo.getQuickplay_global().getHealing_done_average();
            healChart.setContent("치유-평균", "", (int) Math.max((Math.ceil(heal / 100.0f) * 100),(Math.ceil(compareHeal / 100.0f) * 100)), heal, compareHeal);

        }
//
//        // 백그라운드
//        Glide
//                .with(mContext)
//                .load(R.drawable.portrait_background)
//                .crossFade()
//                .fitCenter()
//                .into(new ViewTarget<View, GlideDrawable>(mCompareViewBackground) {    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        this.view.setBackground(resource);
//                    }
//                });



    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if( App.getCurrentUserInfo() == null ) {
            buttonView.setChecked(!isChecked);
            return;
        }

        showContentView(isChecked);
    }

    private SummaryChangeListener mSummaryChangeListener = null;
    public void setSummaryChangeListener(SummaryChangeListener listener) {
        mSummaryChangeListener = listener;
    }

    public void showContentView( boolean isCompareState ) {
        // 비교
        mTimeStampTextView.setText(mUserInfo.getTimestamp());

        if( isCompareState ) {
            mSingleViewContainer.setVisibility(View.GONE);
            mCompareViewContainer.setVisibility(View.VISIBLE);
            configureCompareView(mContainer);
        } else {
            mSingleViewContainer.setVisibility(View.VISIBLE);
            mCompareViewContainer.setVisibility(View.GONE);
            configureSingleView(mContainer);
        }
        App.setCompareState( isCompareState);
    }

    @Override
    public void onSummaryChanged(int summary) {
        showContentView( App.getIsCompareState() );
    }
}

