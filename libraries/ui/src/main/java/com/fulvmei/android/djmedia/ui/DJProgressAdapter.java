package com.fulvmei.android.djmedia.ui;


import com.fulvmei.android.media.ui.DefaultProgressAdapter;

public class DJProgressAdapter extends DefaultProgressAdapter {

    @Override
    public boolean showSeekView() {
        return isCurrentWindowSeekable() && !isCurrentWindowLive();
    }

    @Override
    public boolean showPositionViewView() {
        return isCurrentWindowSeekable() && !isCurrentWindowLive();
    }

    @Override
    public boolean showDurationView() {
        return isCurrentWindowSeekable() && !isCurrentWindowLive();
    }

}
