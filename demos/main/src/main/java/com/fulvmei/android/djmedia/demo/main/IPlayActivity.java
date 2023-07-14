package com.fulvmei.android.djmedia.demo.main;

import com.fulvmei.android.djmedia.demo.main.been.Video;

public interface IPlayActivity {
    default void onVideoScreenChanged(boolean fullScreen, boolean portrait) {
    }

    default boolean onVideoRetryClick(Video video) {
        return false;
    }
}
