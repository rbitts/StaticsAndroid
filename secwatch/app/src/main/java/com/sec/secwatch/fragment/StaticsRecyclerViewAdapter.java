package com.sec.secwatch.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.sec.secwatch.AnimationUtil;
import com.sec.secwatch.App;
import com.sec.secwatch.FontManager;
import com.sec.secwatch.R;
import com.sec.secwatch.widgets.FavoriteView;
import com.sec.secwatch.wrapper.UserInfoAllWrapper;
import com.sec.secwatch.wrapper.UserInfoWrapper;
import com.sec.widget.LabelView.LabelTextView;
import com.sec.widget.ShineButton.ShineButton;

import java.util.ArrayList;
import java.util.logging.Handler;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by rbitt on 2017-04-26.
 */
public class StaticsRecyclerViewAdapter extends RecyclerView.Adapter<StaticsRecyclerViewAdapter.StaticsViewHolder> implements RecyclerViewClickListener {

    private ArrayList<UserInfoWrapper> users;

    private Context mContext = null;

    private View mPreviousClickedView = null;

    private RecyclerViewClickListener mRecyclerViewClickListener = null;

    public StaticsRecyclerViewAdapter(ArrayList<UserInfoWrapper> users) {
        if (users == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.users = users;
    }

    @Override
    public StaticsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.statics_place_holder, viewGroup, false);
        return new StaticsViewHolder(itemView, this);
    }


    @Override
    public void onBindViewHolder(StaticsViewHolder holder, int position) {
        UserInfoWrapper wrapper = users.get(position);
        if( wrapper == null )
            return;

        Glide
                .with(mContext)
                .load(R.drawable.portrait_background)
                .centerCrop()
                .crossFade()
                .into(new ViewTarget<View, GlideDrawable>(holder.mPortraitViewBackground) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource);
                    }
                });

        Glide
                .with(mContext)
                .load(Uri.parse(wrapper.getPortrait()))
                .fitCenter()
                .crossFade()
                .into(holder.mPortraitView);

        Glide
                .with(mContext)
                .load(wrapper.getProfile().getAvatar())
                .centerCrop()
                .crossFade()
                .into(holder.mAvatarView);

        Glide
                .with(mContext)
                .load(wrapper.getProfile().getRankPicture())
                .centerCrop()
                .crossFade()
                .into(holder.mRankPictureView);

        Glide
                .with(mContext)
                .load(wrapper.getProfile().getLevelBackground())
                .centerCrop()
                .crossFade()
                .into(holder.mLevelBackgroundView);

        Glide
                .with(mContext)
                .load(wrapper.getProfile().getLevelPicture())
                .centerCrop()
                .crossFade()
                .into(holder.mLevelStarView);

        holder.mIDView.setText( wrapper.getId() );
        holder.mRankView.setText( String.valueOf(wrapper.getProfile().getRank()) );
        holder.mLevelView.setText( String.valueOf(wrapper.getProfile().getLevel()) );
        holder.mDetailView.setText( String.valueOf( wrapper.getGlobal().getDamage_done_average() ));
        holder.mRefreshTimeView.setText( String.format("%s %s", mContext.getResources().getString(R.string.fa_icon_clock), wrapper.getTimestamp()));
        holder.setUserLabel(wrapper.getRole());
        holder.configure();
    }

    public void setRecyclerViewClickListener(RecyclerViewClickListener listener) {
        mRecyclerViewClickListener = listener;
    }

    @Override
    public void onHoverClick(View view) {
        if( mPreviousClickedView != null && mPreviousClickedView != view) {
            View v = mPreviousClickedView.findViewById(R.id.statics_default_container);
            Blurry
                    .delete((ViewGroup) v);

//            ViewCompat.animate().alpha(0.0f).setDuration(220).start();
            AnimationUtil.fadeOut( mPreviousClickedView.findViewById(R.id.statics_hover_container) , 230);
        }
        mPreviousClickedView = view;
        if( mRecyclerViewClickListener != null ) {
            mRecyclerViewClickListener.onHoverClick(view);
        }
    }

    @Override
    public void onUserStaticsViewClick(String userID) {
        if( mRecyclerViewClickListener != null ) {
            mRecyclerViewClickListener.onUserStaticsViewClick(userID);
        }
    }


    @Override
    public void onClick(View view) {
        if( mRecyclerViewClickListener != null ) {
            mRecyclerViewClickListener.onClick(view);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class StaticsViewHolder extends RecyclerView.ViewHolder implements ShineButton.OnCheckedChangeListener {

        private View mPortraitViewBackground;

        private ImageView mPortraitView;

        private ImageView mAvatarView;

        private ImageView mRankPictureView;

        private ImageView mLevelBackgroundView;

        private ImageView mLevelStarView;

        private TextView mLevelView;

        private TextView mIDView;

        private TextView mRankView;

        private TextView mDetailView;

        private TextView mRefreshTimeView;

        private View mDefaultView = null;

        private View mHoverView = null;

        private Context mContext = null;

        private ShineButton mFavoriteView = null;

        private LabelTextView mRoleView = null;

        private TextView mToastView = null;

        private RecyclerViewClickListener mClickListener = null;

        public StaticsViewHolder(final View itemView, RecyclerViewClickListener listener) {

            super(itemView);
            mContext = itemView.getContext();

            mDefaultView = itemView.findViewById(R.id.statics_default_container);
            mHoverView = itemView.findViewById(R.id.statics_hover_container);

            mPortraitViewBackground = itemView.findViewById(R.id.statics_portrait_background);
            mPortraitView = (ImageView) itemView.findViewById(R.id.statics_portrait);
            mAvatarView = (ImageView) itemView.findViewById(R.id.statics_avatar);
            mRankPictureView = (ImageView) itemView.findViewById(R.id.statics_rank_picture);
            mLevelBackgroundView = (ImageView) itemView.findViewById(R.id.statics_level_background);
            mLevelStarView = (ImageView) itemView.findViewById(R.id.statics_level_star);
            mLevelView = (TextView) itemView.findViewById(R.id.statics_level);
            mIDView = (TextView) itemView.findViewById(R.id.statics_id);
            mRankView = (TextView) itemView.findViewById(R.id.statics_rank);
            mDetailView = (TextView) itemView.findViewById(R.id.statics_detail);
            mRefreshTimeView = (TextView) itemView.findViewById(R.id.statics_refresh_time);
            mRoleView = (LabelTextView) itemView.findViewById(R.id.statics_role_view);
            mToastView = (TextView) itemView.findViewById(R.id.static_user_toast_message);

            // for icons
            Typeface iconFont = FontManager.getTypeface(mContext, FontManager.FONTAWESOME);
            FontManager.markAsIconContainer(mRefreshTimeView, iconFont);
            FontManager.markAsIconContainer(itemView.findViewById(R.id.statics_hover_container), iconFont);

            mFavoriteView = (ShineButton) itemView.findViewById(R.id.statics_favorite);
            mFavoriteView.setOnCheckStateChangeListener( this );

            mClickListener = listener;
            itemView.findViewById(R.id.statics_user_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mClickListener != null) {
                        String userID = mIDView.getText().toString();
                        mClickListener.onUserStaticsViewClick(userID);
                    }
                }
            });

            mDefaultView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mClickListener != null) {
                        mClickListener.onHoverClick(itemView);
                    }
                    showHoverView();
                }
            });

//            mHoverView.findViewById(R.id.)
            mHoverView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideHoverView();
                }
            });
            initViewState();

        }

        private void initViewState() {
            mDefaultView.setVisibility(View.VISIBLE);
            mHoverView.setVisibility(View.GONE);
            mToastView.setVisibility(View.INVISIBLE);
        }


        public void hideHoverView() {
            Blurry
                    .delete((ViewGroup) mDefaultView);

            AnimationUtil.fadeOut(mHoverView, 230);
//            mHoverView.setVisibility(View.GONE);
        }

        public void showHoverView() {
            Blurry.with(mContext)
                    .radius(25)
                    .sampling(4)
                    .animate(230)
                    .onto((ViewGroup) mDefaultView);
            AnimationUtil.fadeIn(mHoverView, 230);
        }

        public void configure() {
            mFavoriteView.setChecked(App.isFavorite((String) mIDView.getText()));
        }

        public void setUserLabel(int userType) {
            switch(userType) {
                case UserInfoAllWrapper.USER_TYPE_TEAM_LEADER:
                    mRoleView.setLabelText("팀장");
                    mRoleView.setVisibility(View.VISIBLE);
                    break;
                case UserInfoAllWrapper.USER_TYPE_LEADER:
                    mRoleView.setLabelText("회장");
                    mRoleView.setVisibility(View.VISIBLE);
                    break;
                default:
                    mRoleView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCheckedChanged(View view, boolean checked) {
            String id = (String) mIDView.getText();
            App.setFavorite(id, checked);
            if( checked ) {
                showToast("즐겨찾기에 추가되었습니다.");
            }
        }

        public void showToast(String message) {
            mToastView.setText(message);
            AnimationUtil.fadeIn(mToastView, 330);

            mToastView.postDelayed(new Runnable() {
                public void run() {
                    AnimationUtil.fadeOut(mToastView, 330);
                }
            }, 1000);
        }
    }

}
