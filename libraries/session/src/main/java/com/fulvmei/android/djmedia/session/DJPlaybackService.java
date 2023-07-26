package com.fulvmei.android.djmedia.session;

import static androidx.media3.session.LibraryResult.RESULT_ERROR_NOT_SUPPORTED;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.session.LibraryResult;
import androidx.media3.session.MediaSession;

import com.fulvmei.android.media.session.PlaybackService;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class DJPlaybackService extends PlaybackService {

    public static final String FU_COMMAND_SET_TIMING_OFF = "FU_COMMAND_SET_TIMING_OFF";
    public static final String FU_COMMAND_SET_TIMING_OFF_VALUE = "FU_COMMAND_SET_TIMING_OFF_VALUE";

    private TimingOff currentTimingOff = TimingOff.defaultTimingOff();
    private CountDownTimer countDownTimer;

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

    private void updateTimingOff(long timeMs) {
        closeCountDownTimer();
        if (currentTimingOff.getSecond() != 0) {
            countDownTimer = new CountDownTimer(currentTimingOff.getSecond(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
//                    currentTimingOff.setFinishedSecond((int) (millisUntilFinished / 1000));
//                    invalidateMediaSessionExtras();
                }

                @Override
                public void onFinish() {
//                    currentTimingOff = TimingOff.defaultTimingOff();
//                    invalidateMediaSessionExtras();
//                    release();
                }
            };
            countDownTimer.start();
        }
    }

    private void closeCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    protected class DJPlaybackSessionCallback extends PlaybackSessionCallback {

        @NonNull
        @Override
        public ListenableFuture<LibraryResult<Void>> onSearch(@NonNull MediaLibrarySession session, @NonNull MediaSession.ControllerInfo browser, @NonNull String query, @Nullable LibraryParams params) {
            if (query.equals(FU_COMMAND_SET_TIMING_OFF)) {
                int timeMs = 0;
                if (params != null) {
                    timeMs = params.extras.getInt(FU_COMMAND_SET_TIMING_OFF_VALUE);
                }
                updateTimingOff(timeMs);
            }
            Log.e("eeee","onSearch query="+query);
            return super.onSearch(session, browser, query, params);
        }

        @NonNull
        @Override
        public ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> onGetSearchResult(@NonNull MediaLibrarySession session, @NonNull MediaSession.ControllerInfo browser, @NonNull String query, int page, int pageSize, @Nullable LibraryParams params) {
            return Futures.immediateFuture(LibraryResult.ofError(RESULT_ERROR_NOT_SUPPORTED));
//            return super.onGetSearchResult(session, browser, query, page, pageSize, params);
        }
    }
}
