package com.fulvmei.android.djmedia.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulvmei.android.media.ui.SampleEndedView;

public class DJVideoEndedView extends SampleEndedView {

    public DJVideoEndedView(@NonNull Context context) {
        super(context);
    }

    public DJVideoEndedView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DJVideoEndedView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        updateFullScreen(fullScreen);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.fu_view_video_state_ended, parent, false);
    }

    @Override
    protected void onFullScreenChanged(boolean fullScreen) {
        updateFullScreen(fullScreen);
    }


    protected void updateFullScreen(boolean fullScreen) {
        View retry = findViewById(com.fulvmei.android.media.ui.R.id.fu_state_ended_replay);
        if (retry == null) {
            return;
        }
        if (fullScreen) {
            retry.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
            retry.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
        } else {
            retry.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            retry.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        }
    }
}
