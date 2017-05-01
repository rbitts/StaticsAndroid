package com.sec.widget.Chart.animation;

import com.sec.widget.Chart.model.ChartSet;

import java.util.ArrayList;


public interface ChartAnimationListener {

	boolean onAnimationUpdate(ArrayList<ChartSet> data);
}
