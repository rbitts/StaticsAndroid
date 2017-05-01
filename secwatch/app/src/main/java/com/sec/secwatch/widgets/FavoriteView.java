package com.sec.secwatch.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.sec.secwatch.FontManager;
import com.sec.secwatch.R;

/**
 * Created by rbitt on 2017-04-28.
 */

@SuppressLint("AppCompatCustomView")
public class FavoriteView extends TextView {

    private String mDefaultText;

    private String mActiveText;

    private boolean isActive = false;

    private int mDefaultColor;

    private int mActiveColor;

    public interface FavoriteActiveListener {
        void onActiveChanged(boolean active);
    }

    private FavoriteActiveListener mFavoriteActiveListener = null;

    public FavoriteView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

        Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(this, iconFont);

        mDefaultText = context.getResources().getString(R.string.fa_star_o);
        mDefaultColor = this.getCurrentTextColor();

        mActiveText = context.getResources().getString(R.string.fa_star);
        mActiveColor = context.getResources().getColor(R.color.favorite_active_color);

        this.setText(mDefaultText);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch ( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                setActive(!isActive);
                return true;
        }

        return super.onTouchEvent(event);
    }

    public void setFavoriteActiveListener(FavoriteActiveListener listener) {
        mFavoriteActiveListener = listener;
    }

    public void setActive(boolean active) {
        if( active ) {
            this.setText(mActiveText);
            this.setTextColor(mActiveColor);
        } else {
            this.setText(mDefaultText);
            this.setTextColor(mDefaultColor);
        }
        isActive = active;
        if( mFavoriteActiveListener != null ) {
            mFavoriteActiveListener.onActiveChanged(isActive);
        }
    }

}
