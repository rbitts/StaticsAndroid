package com.sec.secwatch.userview;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.sec.secwatch.R;
import com.sec.secwatch.wrapper.GameInfoHeroData;
import com.sec.secwatch.wrapper.GameInfoConverter;
import com.sec.widget.RoundCornerProgressBar.IconRoundCornerProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by rbitt on 2017-04-30.
 */

public class UserViewPlayTimeAdapter extends RecyclerView.Adapter<UserViewPlayTimeAdapter.UserViewPlayTimeViewHolder>  {

    private ArrayList<GameInfoHeroData> listViewItemList = null;

    private int mProgressMaxValue = 0;

    private Context mContext = null;

    public UserViewPlayTimeAdapter(ArrayList<GameInfoHeroData>  data) {
        listViewItemList = data;
        Collections.sort(listViewItemList ,myComparator);

        mProgressMaxValue = listViewItemList.get(0).getDetail().getTime_played();

    }

    private final static Comparator myComparator= new Comparator<GameInfoHeroData>() {

        @Override
        public int compare(GameInfoHeroData o1, GameInfoHeroData o2) {
            Integer lhs = o1.getDetail().getTime_played();
            Integer rhs = o2.getDetail().getTime_played();
            return rhs.compareTo(lhs);
        }

    };

    @Override
    public UserViewPlayTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.user_view_time_play_view_holder, parent, false);

        return new UserViewPlayTimeViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final UserViewPlayTimeViewHolder holder, int position) {
        GameInfoHeroData info = listViewItemList.get(position);
        if( info == null )
            return;

        String thumbURL = info.getThumb().replace("/img/heros/" , "file:///android_asset/small/");
//        Log.d(App.TAG, String.format("%d ", mProgressMaxValue));
        holder.mProgressView.setMax( mProgressMaxValue );
        holder.mProgressView.setProgress( info.getDetail().getTime_played());
        holder.mProgressView.setSecondaryProgress( mProgressMaxValue);

        holder.mProgressView.setIconImageResource(GameInfoConverter.getSmallPortraitResourceFromString(info.getDisplay_name()));
        holder.mProgressView.invalidate();
//        Glide
//                .with(mContext)
//                .load(Uri.parse(thumbURL))
//                .fitCenter()
//                .into(new ViewTarget<View, GlideDrawable>(holder.mProgressView) {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        holder.mProgressView.setIconDrawable(resource);
//                    }
//                });

        holder.mUserViewHeroTextView.setText(info.getDisplay_name());
        holder.mContentTextView.setText(GameInfoConverter.getTimeString(info.getDetail().getTime_played()));
    }

    @Override
    public int getItemCount() {
        return listViewItemList.size();
    }


    public static class UserViewPlayTimeViewHolder extends RecyclerView.ViewHolder {

        private IconRoundCornerProgressBar mProgressView = null;

        private TextView mContentTextView = null;

        private TextView mUserViewHeroTextView = null;

        public UserViewPlayTimeViewHolder(View itemView) {
            super(itemView);

            mProgressView = (IconRoundCornerProgressBar) itemView.findViewById(R.id.user_view_list_progress);
            mContentTextView = (TextView) itemView.findViewById(R.id.user_view_time_played_text);
            mUserViewHeroTextView = (TextView) itemView.findViewById(R.id.user_view_hero_text);
        }
    }
}
