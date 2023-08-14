package com.fulvmei.android.djmedia.demo.main;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import com.fulvmei.android.djmedia.ui.DJVideoBufferingView;
import com.fulvmei.android.media.ui.PlayerControlView;
import com.fulvmei.android.media.ui.PlayerView;

public class ListPlayItemView extends FrameLayout {

    Player player;
    PlayerView playerView;
    PlayerControlView playerControlView;
    DJVideoBufferingView bufferingView;

    public ListPlayItemView(Context context) {
        this(context, null);
    }

    public ListPlayItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListPlayItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.item_list_play, this);

        player = new ExoPlayer.Builder(context).build();
        player.setMediaItem(MediaItem.fromUri("https://wsvod.gzstv.com/uploads/media/gzxwlb/1106jinbohuiVA1.mp4"));

        playerView = findViewById(R.id.playerView);
        playerView.setPlayer(player);

        bufferingView = findViewById(R.id.bufferingView);
        bufferingView.setPlayer(player);

        playerControlView = findViewById(R.id.playerControlView);
        playerControlView.setPlayer(player);

        playerControlView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ad = getGlobalVisibleRect(new Rect());
                Log.e("eee","getGlobalVisibleRect="+ad);
            }
        });
    }

    public void start(){
        player.prepare();
        player.play();
    }

    public void stop(){
        player.stop();
    }
}
