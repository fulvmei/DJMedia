package com.fulvmei.android.djmedia.demo.main;

import androidx.annotation.NonNull;
import androidx.media3.common.Player;
import androidx.recyclerview.widget.RecyclerView;

public class ListPlayItemViewHolder extends RecyclerView.ViewHolder {

    ListPlayItemView playView;

    public ListPlayItemViewHolder(@NonNull ListPlayItemView itemView) {
        super(itemView);
        playView = itemView;
    }

    public void start() {
        playView.start();
    }

    public void stop() {
        playView.stop();
    }

}
