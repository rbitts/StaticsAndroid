package com.sec.secwatch.userview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sec.secwatch.R;
import com.sec.widget.Chart.Tools;
import com.sec.widget.Chart.animation.Animation;
import com.sec.widget.Chart.model.Bar;
import com.sec.widget.Chart.model.BarSet;
import com.sec.widget.Chart.renderer.XRenderer;
import com.sec.widget.Chart.view.HorizontalBarChartView;

/**
 * Created by rbitt on 2017-04-30.
 */

public class UserViewSummarySingleView extends FrameLayout {

    private Context mContext = null;

    private HorizontalBarChartView mSingleChartView = null;

    private TextView mSingleChartTitleView = null;

    private TextView mSingleChartDescriptView = null;

    private String mTitle = null;

    private String mDescript = null;

    private int mAxisValue = 1;

    private float mValue = 0.0f;

    public UserViewSummarySingleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.summary_single_place_holder, this);
    }

    @Override
    protected void onFinishInflate() {

        mSingleChartView = (HorizontalBarChartView) this.findViewById(R.id.user_view_summary_single_chart);
        mSingleChartTitleView = (TextView) findViewById(R.id.user_view_summary_single_title);
        mSingleChartDescriptView = (TextView) findViewById(R.id.user_view_summary_single_descript);

        super.onFinishInflate();
    }

    public void setContent(String title, String unit, int axisValue,float value) {

        mTitle = title;
        mDescript = String.format("%.1f%s", value, unit);
        mAxisValue = axisValue;
        mValue = value;

        show();
    }

    private void show() {

        mSingleChartTitleView.setText(mTitle);
        mSingleChartDescriptView.setText(mDescript);

        mSingleChartView.getData().clear();
        mSingleChartView.reset();

        BarSet barSet = new BarSet();
        Bar bar;
        bar = new Bar("", mValue);
        bar.setColor(Color.parseColor("#27a9e3"));
        barSet.addBar(bar);
        mSingleChartView.addData(barSet);
        mSingleChartView.setBarSpacing(Tools.fromDpToPx(4));

        mSingleChartView.setBorderSpacing(0)
                .setXAxis(false)
                .setYAxis(false)
                .setXLabels(XRenderer.LabelPosition.NONE)
                .setAxisBorderValues(0, mAxisValue);

        Animation anim = new Animation()
                .setEasing(new BounceInterpolator())
                .setDuration(420);

        mSingleChartView.show(anim);
    }
}
