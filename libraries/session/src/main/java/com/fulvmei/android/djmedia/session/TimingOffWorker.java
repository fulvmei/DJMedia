package com.fulvmei.android.djmedia.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class TimingOffWorker extends Worker {

    public TimingOffWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("eeee","TimingOffWorker doWork");
        return Result.success();
    }

    public static void startWork(@NonNull Context context,long duration) {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(TimingOffWorker.class)
                .setInitialDelay(duration, TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(context).enqueueUniqueWork("TimingOffWorker", ExistingWorkPolicy.REPLACE, request);
    }

    public static void stopWork(@NonNull Context context) {
        WorkManager.getInstance(context).cancelUniqueWork("TimingOffWorker");
    }

}
