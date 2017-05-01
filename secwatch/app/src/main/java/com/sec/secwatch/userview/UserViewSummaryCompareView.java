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
import com.sec.widget.Chart.renderer.AxisRenderer;
import com.sec.widget.Chart.view.HorizontalStackBarChartView;

import java.util.ArrayList;

/**
 * Created by rbitt on 2017-04-30.
 */

public class UserViewSummaryCompareView extends FrameLayout{

    private Context mContext = null;

    private HorizontalStackBarChartView mCompareChartView = null;

    private TextView mCompareChartTitleView = null;

    private TextView mChartDescriptView = null;
    private TextView mCompareChartDescriptView = null;

    private String mTitle = null;

    private String mUnitText = null;

    private float mCompareValue = 0.0f;

    private float mValue = 0.0f;

    private int mAxisValue = 0;

    public UserViewSummaryCompareView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.summary_compare_place_holder, this);
    }

    @Override
    protected void onFinishInflate() {

        mCompareChartView = (HorizontalStackBarChartView) this.findViewById(R.id.user_view_summary_compare_chart);
        mCompareChartTitleView = (TextView) findViewById(R.id.user_view_summary_compare_title);
        mChartDescriptView = (TextView) findViewById(R.id.user_view_summary_chart_desc);
        mCompareChartDescriptView = (TextView) findViewById(R.id.user_view_summary_compare_chart_desc);

        super.onFinishInflate();
    }

    public void setContent(String title, String unit, int axisValue, float value1, float value2) {

        mTitle = title;
        mValue = value1;
        mUnitText = unit;
        mCompareValue = value2;
        mAxisValue = axisValue;

        show();
    }

    private void show() {

        mCompareChartTitleView.setText(mTitle);

        mChartDescriptView.setText(String.format("%.1f%s",mValue,mUnitText));
        mCompareChartDescriptView.setText(String.format("%.1f%s",mCompareValue,mUnitText));

        mCompareChartView.getData().clear();
        mCompareChartView.reset();

        BarSet barSet = new BarSet();
        Bar bar;
        bar = new Bar("", -( mValue ));
        bar.setColor(Color.parseColor("#27a9e3"));
        barSet.addBar(bar);
        mCompareChartView.addData(barSet);

        barSet = new BarSet();
        bar = new Bar("", mCompareValue);
        barSet.addBar(bar);
        bar.setColor(Color.parseColor("#ffb848"));
        mCompareChartView.addData(barSet);

        mCompareChartView.setRoundCorners(Tools.fromDpToPx(5));
        mCompareChartView.setBarSpacing(Tools.fromDpToPx(10));

        mCompareChartView.setBorderSpacing(Tools.fromDpToPx(5))
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setXLabels(AxisRenderer.LabelPosition.NONE)
                .setXAxis(false)
                .setYAxis(false)
                .setAxisBorderValues(-mAxisValue, mAxisValue, mAxisValue);

        Animation anim = new Animation()
                .setEasing(new BounceInterpolator())
                .setDuration(420);

        mCompareChartView.show(anim);

    }
}


