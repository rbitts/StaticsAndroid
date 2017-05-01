package com.sec.secwatch;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.sec.widget.ShineButton.Ease;
import com.sec.widget.ShineButton.EasingInterpolator;

/**
 * Created by rbitt on 2017-04-27.
 */

public class AnimationUtil {

    public static void fadeOut(final View view , int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), 0.0f).setDuration(duration);
        animator.setInterpolator(new EasingInterpolator(Ease.QUART_OUT));
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }

    public static void fadeIn(final View view , int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), 1.0f).setDuration(duration);
        animator.setInterpolator(new EasingInterpolator(Ease.QUART_IN_OUT));
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }
}
