package com.example.chaozhou.module.share.x;

import android.view.MotionEvent;

public interface GestureDetector {
    boolean onTouchEvent(MotionEvent var1);

    boolean isScaling();

    void setOnGestureListener(OnGestureListener var1);
}

