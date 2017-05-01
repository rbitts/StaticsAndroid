package com.sec.secwatch.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sec.secwatch.FontManager;

/**
 * Created by rbitt on 2017-04-29.
 */

@SuppressLint("AppCompatCustomView")
public class AwesomeTextView extends TextView {

    public AwesomeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(this, iconFont);
    }

}
