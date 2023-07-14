package com.fulvmei.android.djmedia.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulvmei.android.media.ui.SampleErrorView;


public class DJVideoPlayErrorView extends SampleErrorView {

    public DJVideoPlayErrorView(@NonNull Context context) {
        super(context);
    }

    public DJVideoPlayErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DJVideoPlayErrorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.fu_view_video_state_error, parent, false);
    }
}
