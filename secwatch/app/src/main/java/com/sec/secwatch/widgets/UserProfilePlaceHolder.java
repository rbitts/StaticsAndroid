package com.sec.secwatch.widgets;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rbitt on 2017-04-24.
 */

public class UserProfilePlaceHolder extends FrameLayout {

    private ImageView mAvatarView = null;

    private ImageView mRankPictureView = null;

    private TextView mRankTextView = null;

    private ImageView mLevelBackgroundView = null;

    private ImageView mLevelStarView = null;

    private TextView mLevelTextView = null;

    private TextView mTimestampView = null;

    private TextView mIDView = null;

    private TextView mGamesWonView = null;

    private View mPortraitView = null;

    private View mPlaceHolderView = null;

    private View mDefaultPlaceHolderView = null;

    private UserInfoAllWrapper mUserInfo = null;

    public interface UserProfileClickListener {
        void onBlankProfileClick() ;
        void onProfileClick() ;
    }

    private UserProfileClickListener mUserProfileClickListener = null;

    private AsyncHttpResponseHandler mResponseHandler =new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                UserInfoAllWrapper wrapper = mapper.readValue(responseBody, UserInfoAllWrapper.class);
                setDataSet(wrapper);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            showDefault();
        }
    };


    public UserProfilePlaceHolder(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.profile_place_holder, this);

    }

    public void requestProfile(String id) {
        getUserStatics.get(id, mResponseHandler);
    }

    public void setDataSet ( UserInfoAllWrapper dataSet ) {
        if( dataSet == null) {
            return;
        }

        mUserInfo= dataSet;

        setID( dataSet.getId() );
        setAvatar( dataSet.getProfile().getAvatar());
        setTimeStamp(dataSet.getTimestamp());
        setPortrait( dataSet.getPortrait() );
        setGamesWon( String.valueOf(dataSet.getGlobal().getDeaths_average() ));

        setLevelText( dataSet.getProfile().getLevel() );
        setLevelBackgroundView( dataSet.getProfile().getLevelBackground());
        setLevelStarView( dataSet.getProfile().getLevelPicture());

        setRankText( dataSet.getProfile().getRank() );
        setRankPicture( dataSet.getProfile().getRankPicture());

        showPortrait();

    }

    public UserInfoAllWrapper getUserInfo() {
        return mUserInfo;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDefaultPlaceHolderView = findViewById(R.id.profile_default_place_holder);
        mDefaultPlaceHolderView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUserProfileClickListener != null) {
                    mUserProfileClickListener.onBlankProfileClick();
                }
            }
        });
        mPlaceHolderView = findViewById(R.id.profile_place_holder_user_container);
        mPlaceHolderView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUserProfileClickListener != null) {
                    mUserProfileClickListener.onProfileClick();
                }
            }
        });

        mAvatarView = (ImageView) findViewById(R.id.profile_place_holder_avatar);
        mTimestampView = (TextView) findViewById(R.id.profile_place_holder_time_stamp);
        mPortraitView = findViewById(R.id.profile_place_holder_portrait);
        mIDView = (TextView) findViewById(R.id.profile_place_holder_id);
        mGamesWonView = (TextView) findViewById(R.id.profile_place_holder_won);
        mRankPictureView = (ImageView) findViewById(R.id.profile_place_holder_rank_picture);
        mRankTextView = (TextView) findViewById(R.id.profile_place_holder_rank_text);
        mLevelBackgroundView = (ImageView) findViewById(R.id.profile_place_holder_level_background);
        mLevelStarView = (ImageView) findViewById(R.id.profile_place_holder_level_star);
        mLevelTextView = (TextView) findViewById(R.id.profile_place_holder_level_text);
        showDefault();

    }

    public void setUserProfileClickListener(UserProfileClickListener listener) {
        mUserProfileClickListener = listener;
    }

    public void setAvatar(String imageURL) {
        if( mAvatarView == null ) {
            Log.d(App.TAG, " avatar view is null ");
            return ;
        }
        if( imageURL == null || imageURL.isEmpty() ) {
            Glide.with(App.getContext()).load(R.drawable.user_icon).into(mAvatarView);
            return;
        }
        Glide.with(App.getContext()).load(imageURL).into(mAvatarView);
    }

    public void setPortrait(String imageURL) {
//        imageURL = getResources().getString(R.string.serverURL) +imageURL;

        if( mPortraitView == null ) {
            Log.d(App.TAG, " avatar view is null ");
            return ;
        }
        if( imageURL == null || imageURL.isEmpty() ) {
            Glide.with(App.getContext()).load("").into(new ViewTarget<View, GlideDrawable>(mPortraitView) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    this.view.setBackground(null);
                }
            });
            return;
        }
        Glide.with(App.getContext()).load(Uri.parse(imageURL)).centerCrop().into(new ViewTarget<View, GlideDrawable>(mPortraitView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                this.view.setBackground(resource);
            }
        });
    }

    public void setID(String id) {
        if(mIDView== null || id == null || id.isEmpty() )  return;
        mIDView.setText(id.replace("-","#"));
    }

    public void setGamesWon(String won) {
        if(mGamesWonView == null || won == null || won.isEmpty() )  return;
        mGamesWonView.setText(won);
    }

    public  void setTimeStamp(String timeStamp) {
        if(mTimestampView== null)  return;

        if( timeStamp == null || timeStamp.isEmpty() ) {
            mTimestampView.setText("갱신기록없음");
            return;
        }

        mTimestampView.setText(timeStamp);
    }

    public  void setRankText(int rankText) {
        if(mRankTextView== null)  return;

        if( rankText == 0 ) {
            mRankTextView.setText("기록없음");
            return;
        }

        mRankTextView.setText(String.valueOf(rankText));
    }



    private void setRankPicture(String imageURL) {
        if( mRankPictureView == null ) {
            return ;
        }
        if( imageURL == null || imageURL.isEmpty() ) {
            Glide.with(App.getContext()).load(R.drawable.user_icon).into(mRankPictureView);
            return;
        }
        Glide.with(App.getContext()).load(imageURL).into(mRankPictureView);
    }


    private void setLevelBackgroundView(String imageURL) {
        if( mLevelBackgroundView == null ) {
            return ;
        }
        if( imageURL == null || imageURL.isEmpty() ) {
            Glide.with(App.getContext()).load(R.drawable.user_icon).into(mLevelBackgroundView);
            return;
        }
        Glide.with(App.getContext()).load(imageURL).into(mLevelBackgroundView);
    }


    private void setLevelStarView(String imageURL) {
        if( mLevelStarView == null ) {
            return ;
        }
        if( imageURL == null || imageURL.isEmpty() ) {
            Glide.with(App.getContext()).load(R.drawable.user_icon).into(mLevelStarView);
            return;
        }
        Glide.with(App.getContext()).load(imageURL).into(mLevelStarView);
    }

    private void setLevelText(int level) {
        if(mLevelTextView== null)  return;

        if( level == 0 ) {
            mLevelTextView.setText("기록없음");
            return;
        }

        mLevelTextView.setText(String.valueOf(level));
    }

    private void showPortrait() {
        mPlaceHolderView.setVisibility(VISIBLE);
        mPortraitView.setVisibility(VISIBLE);
        mDefaultPlaceHolderView.setVisibility(GONE);
    }

    private void showDefault() {
        mPlaceHolderView.setVisibility(GONE);
        mPortraitView.setVisibility(GONE);
        mDefaultPlaceHolderView.setVisibility(VISIBLE);
    }

    public String getUserID() {
        return mIDView.getText().toString();
    }
}
