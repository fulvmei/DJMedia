package com.fulvmei.android.djmedia.session;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

public class Timing implements Parcelable {
    public static final int MODE_OFF = 0;
    public static final int MODE_ONE = 1;
    public static final int MODE_TIME = 2;

    private String name;
    private int mode;
    private long duration;

    protected Timing(Parcel in) {
        name = in.readString();
        mode = in.readInt();
        duration = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(mode);
        dest.writeLong(duration);
    }

    public static final Creator<Timing> CREATOR = new Creator<Timing>() {
        @Override
        public Timing createFromParcel(Parcel in) {
            return new Timing(in);
        }

        @Override
        public Timing[] newArray(int size) {
            return new Timing[size];
        }
    };

    public static Timing defaultTiming() {
        return new Timing("不开启",MODE_OFF, 0);
    }

    public Timing() {
    }

    public Timing(String name, int mode, long duration) {
        this.name = name;
        this.mode = mode;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @NonNull
    @Override
    public String toString() {
        return "Timing{" +
                "mode=" + mode +
                ", duration=" + duration +
                '}';
    }
}
