package com.fulvmei.android.djmedia.demo.session;

import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.Tracks;
import androidx.media3.session.LibraryResult;
import androidx.media3.session.MediaBrowser;
import androidx.media3.session.MediaController;
import androidx.media3.session.MediaLibraryService;
import androidx.media3.session.SessionCommand;
import androidx.media3.session.SessionResult;
import androidx.media3.session.SessionToken;

import com.fulvmei.android.djmedia.session.DJPlaybackService;
import com.fulvmei.android.media.ui.PlayerControlView;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PlayerActivity extends AppCompatActivity {

    //    MediaController mediaController;
    MediaBrowser mediaBrowser;

    PlayerControlView controlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        controlView = findViewById(R.id.controlView);

        ListenableFuture<MediaBrowser> mediaControllerFuture = new MediaBrowser.Builder(this, new SessionToken(this, new ComponentName(this, DJPlaybackService.class)))
                .setListener(new MediaBrowser.Listener() {

                    @NonNull
                    @Override
                    public ListenableFuture<SessionResult> onCustomCommand(@NonNull MediaController controller, @NonNull SessionCommand command, @NonNull Bundle args) {
                        Log.e("GGGG", "onCustomCommand command=" +command);
                        return Futures.immediateFuture(new SessionResult(SessionResult.RESULT_ERROR_NOT_SUPPORTED));
                    }
                })
                .buildAsync();
        mediaControllerFuture.addListener(() -> {
            try {
                mediaBrowser = mediaControllerFuture.get();
                initPlayer();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, MoreExecutors.directExecutor());

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle extras=new Bundle();
//                MediaMetadata mediaMetadata=new MediaMetadata.Builder()
//                        .setTitle("CCCCCCCCCCC")
//                        .setExtras(extras)
//                        .build();
//                MediaItem mediaItem=  new MediaItem.Builder()
//                        .setMediaMetadata(mediaMetadata)
//                        .setUri("https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/02_-_Geisha.mp3")
//                        .build();
//                mediaBrowser.addMediaItem(mediaItem);

//                SessionCommand command = new SessionCommand("COMMAND_GET_TEXT", new Bundle());
//                Bundle args;
////                mediaBrowser.sendCustomCommand(command, new Bundle());
//
////                mediaBrowser.getItem("11111111111111");
//
////                mediaBrowser.getSearchResult("aaaaaaaaaa",0,1,null);
//                Bundle extras=new Bundle();
//                extras.putInt(DJPlaybackService.FU_TIMING_MODE,DJPlaybackService.TIMING_MODE_TIME);
//                extras.putLong(DJPlaybackService.FU_TIMING_DURATION,10*1000);
//                MediaLibraryService.LibraryParams params=new MediaLibraryService.LibraryParams.Builder()
//                        .setExtras(extras)
//                        .build();
//                ListenableFuture<LibraryResult<Void>> resultListenableFuture = mediaBrowser.search(DJPlaybackService.FU_COMMAND_SET_TIMING_MODE, params);
//                try {
//                    Log.e("sdff",resultListenableFuture.get().params+"");
//                } catch (ExecutionException | InterruptedException e) {
//                    throw new RuntimeException(e);
//                }

            }
        });
    }

    public void initPlayer() {
        mediaBrowser.addListener(new Player.Listener() {
            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Log.e("GGGG", "onMediaItemTransition1 mediaItem=" + mediaItem + "  reason=" + reason);
            }
        });

        List<MediaItem> list = new ArrayList<>();

        Bundle extras = new Bundle();
        extras.putSerializable("video", "asdasdasdasdsd");
        MediaMetadata mediaMetadata = new MediaMetadata.Builder()
                .setTitle("AAAAAAAAA")
                .setExtras(extras)
                .build();

        MediaItem mediaItem = new MediaItem.Builder()
                .setMediaMetadata(mediaMetadata)
                .setUri("https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/02_-_Geisha.mp3")

                .build();

        MediaMetadata mediaMetadata2 = new MediaMetadata.Builder().setTitle("BBBBBBBBB").build();
        list.add(mediaItem);
        list.add(MediaItem.fromUri("https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/02_-_Geisha.mp3").buildUpon().setMediaMetadata(mediaMetadata2).build());
        controlView.setPlayer(mediaBrowser);
        mediaBrowser.setMediaItems(list);
//        mediaController.setPlayWhenReady(true);
//        mediaController.prepare();
        mediaBrowser.play();

        controlView.setPlayer(mediaBrowser);

//        mediaBrowser.subscribe("aaa",new )

        mediaBrowser.addListener(new Player.Listener() {
            @Override
            public void onTimelineChanged(Timeline timeline, int reason) {
                Log.e("dddd", "onTimelineChanged reason=" + reason);
            }

            @Override
            public void onTracksChanged(Tracks tracks) {
                Player.Listener.super.onTracksChanged(tracks);
                Log.e("dddd", "onTracksChanged tracks=" + tracks);
            }

            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Log.e("dddd", "onMediaItemTransition");
            }

            @Override
            public void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
                Log.e("dddd", "onPlaylistMetadataChanged");
            }
        });
    }
}