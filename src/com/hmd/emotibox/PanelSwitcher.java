package com.hmd.emotibox;

import android.view.animation.TranslateAnimation;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector;
import android.widget.FrameLayout;
import android.content.Context;
import android.util.AttributeSet;

class PanelSwitcher extends FrameLayout {
    private static final int MAJOR_MOVE = 60;
    private static final int ANIM_DURATION = 400;

    private GestureDetector mGestureDetector;
    private int mCurrentView;
    private View mChildren[] = new View[0];

    private int mWidth;
    private TranslateAnimation inLeft;
    private TranslateAnimation outLeft;

    private TranslateAnimation inRight;
    private TranslateAnimation outRight;

    private static final int LEFT  = 1;
    private static final int RIGHT = 2;

    public PanelSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCurrentView = 0;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    int dx = (int) (e2.getX() - e1.getX());

                    // don't accept the fling if it's too short
                    // as it may conflict with a button push
                    if (Math.abs(dx) > MAJOR_MOVE && Math.abs(velocityX) > Math.abs(velocityY)) {
                        if (velocityX > 0) {
                            moveRight();
                        } else {
                            moveLeft();
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            });
    }

    void setCurrentIndex(int current) {
        mCurrentView = current;
        updateCurrentView();
    }

    private void updateCurrentView() {
        for (int i = mChildren.length-1; i >= 0 ; --i) {
            mChildren[i].setVisibility(i==mCurrentView ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        mWidth = w;
        inLeft   = new TranslateAnimation(mWidth, 0, 0, 0);
        outLeft  = new TranslateAnimation(0, -mWidth, 0, 0);
        inRight  = new TranslateAnimation(-mWidth, 0, 0, 0);
        outRight = new TranslateAnimation(0, mWidth, 0, 0);

        inLeft.setDuration(ANIM_DURATION);
        outLeft.setDuration(ANIM_DURATION);
        inRight.setDuration(ANIM_DURATION);
        outRight.setDuration(ANIM_DURATION);
    }

    protected void onFinishInflate() {
        int count = getChildCount();
        mChildren = new View[count];
        for (int i = 0; i < count; ++i) {
            mChildren[i] = getChildAt(i);
        }
        updateCurrentView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public void moveLeft() {
        //  <--
        if (mCurrentView < mChildren.length - 1 ) {
            mChildren[mCurrentView+1].setVisibility(View.VISIBLE);
            mChildren[mCurrentView+1].startAnimation(inLeft);
            mChildren[mCurrentView].startAnimation(outLeft);
            mChildren[mCurrentView].setVisibility(View.GONE);

            mCurrentView++;
        }
    }

    public void moveRight() {
        //  -->
        if (mCurrentView > 0 ) {
            mChildren[mCurrentView-1].setVisibility(View.VISIBLE);
            mChildren[mCurrentView-1].startAnimation(inRight);
            mChildren[mCurrentView].startAnimation(outRight);
            mChildren[mCurrentView].setVisibility(View.GONE);

            mCurrentView--;
        }
    }

    int getCurrentIndex() {
        return mCurrentView;
    }
    
    
    public void setGestureDetector( GestureDetector gestureDetector ){
        mGestureDetector = gestureDetector;
    }
}

