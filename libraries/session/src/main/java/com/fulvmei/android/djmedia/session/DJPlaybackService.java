package com.fulvmei.android.djmedia.session;

import static androidx.media3.session.LibraryResult.RESULT_ERROR_NOT_SUPPORTED;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.session.LibraryResult;
import androidx.media3.session.MediaSession;
import androidx.media3.session.SessionCommand;
import androidx.media3.session.SessionResult;

import com.fulvmei.android.media.session.PlaybackService;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class DJPlaybackService extends PlaybackService {
    public static final String FU_COMMAND_GET_TIMING = "fu_command_get_timing";
    public static final String FU_COMMAND_SET_TIMING = "fu_command_set_timing";
    public static final String FU_COMMAND_TIMING_CHANGED = "fu_command_timing_changed";
    public static final String FU_COMMAND_GET_TIMING_UNTIL_FINISHED = "fu_command_get_timing_until_finished";
    public static final String FU_COMMAND_TIMING_UNTIL_FINISHED_CHANGED = "fu_command_timing_until_finished_changed";
    public static final String FU_COMMAND_TIMING_FINISHED = "fu_command_timing_finished";
    public static final String FU_KEY_TIMING = "fu_key_timing";
    public static final String FU_KEY_TIMING_UNTIL_FINISHED = "fu_key_timing_until_finished";

    private CountDownTimer countDownTimer;
    private Timing currentTiming;
    private long untilFinished;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @NonNull
    @Override
    protected Player onGetPlayer() {
        Player player = super.onGetPlayer();
        player.addListener(new Player.Listener() {
        });
        return player;
    }

    @NonNull
    @Override
    public PlaybackSessionCallback onGetSessionCallback() {
        return new DJPlaybackSessionCallback();
    }

    private void updateTiming() {
        closeCountDownTimer();
        if (currentTiming.getMode() == Timing.MODE_TIME) {
            untilFinished=currentTiming.getDuration();
            countDownTimer = new CountDownTimer(currentTiming.getDuration(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    untilFinished=millisUntilFinished;
                    Bundle extras = new Bundle();
                    extras.putLong(FU_KEY_TIMING_UNTIL_FINISHED, millisUntilFinished);
                    SessionCommand command = new SessionCommand(FU_COMMAND_TIMING_UNTIL_FINISHED_CHANGED, extras);
                    mediaLibrarySession.broadcastCustomCommand(command, extras);
                }

                @Override
                public void onFinish() {
                    SessionCommand finishedCommand = new SessionCommand(FU_COMMAND_TIMING_FINISHED, new Bundle());
                    mediaLibrarySession.broadcastCustomCommand(finishedCommand, new Bundle());
                    currentTiming = Timing.defaultTiming();
                    notifyTimingChanged();
                    player.stop();
                }
            };
            countDownTimer.start();
        }
    }

    protected void notifyTimingChanged(){
        Bundle extras = new Bundle();
        extras.putParcelable(FU_KEY_TIMING, currentTiming);
        SessionCommand modeChangedCommand = new SessionCommand(FU_COMMAND_TIMING_CHANGED, extras);
        mediaLibrarySession.broadcastCustomCommand(modeChangedCommand, extras);
    }

    private void closeCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public void onUpdateNotification(MediaSession session, boolean startInForegroundRequired) {
        super.onUpdateNotification(session, startInForegroundRequired);
    }

    protected class DJPlaybackSessionCallback extends PlaybackSessionCallback {
        @NonNull
        @Override
        public ListenableFuture<LibraryResult<Void>> onSearch(@NonNull MediaLibrarySession session, @NonNull MediaSession.ControllerInfo browser, @NonNull String query, @Nullable LibraryParams params) {
            switch (query) {
                case FU_COMMAND_GET_TIMING: {
                    Bundle extras = new Bundle();
                    extras.putParcelable(FU_KEY_TIMING, currentTiming);
                    LibraryParams resultParams = new LibraryParams.Builder()
                            .setExtras(extras)
                            .build();
                    return Futures.immediateFuture(LibraryResult.ofError(RESULT_ERROR_NOT_SUPPORTED, resultParams));
                }
                case FU_COMMAND_SET_TIMING:
                    if (params != null) {
                        currentTiming = params.extras.getParcelable(FU_KEY_TIMING);
                        notifyTimingChanged();
                        updateTiming();
                    }
                    return Futures.immediateFuture(LibraryResult.ofError(RESULT_ERROR_NOT_SUPPORTED, null));
                case FU_COMMAND_GET_TIMING_UNTIL_FINISHED: {
                    Bundle extras = new Bundle();
                    extras.putLong(FU_KEY_TIMING_UNTIL_FINISHED, untilFinished);
                    LibraryParams resultParams = new LibraryParams.Builder()
                            .setExtras(extras)
                            .build();
                    return Futures.immediateFuture(LibraryResult.ofError(RESULT_ERROR_NOT_SUPPORTED, resultParams));
                }
            }
            return super.onSearch(session, browser, query, params);
        }
    }
}
