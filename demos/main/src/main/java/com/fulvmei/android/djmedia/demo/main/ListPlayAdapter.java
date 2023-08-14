package com.fulvmei.android.djmedia.demo.main;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class ListPlayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int lastPlayPosition = -1;
    private ListPlayItemViewHolder currentPlayVH;
    private Map<Integer, ListPlayItemViewHolder> map = new HashMap<>();
    private final Set<ListPlayItemViewHolder> attachedHolders;
    private ListPlayItemViewHolder activeHolder;

    public final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                ListPlayItemViewHolder firstCompletelyVisiblePlayableItem = findFirstCompletelyVisiblePlayableItem(layoutManager);
                if (activeHolder != firstCompletelyVisiblePlayableItem) {
                    if (activeHolder != null) {
                        activeHolder.stop();
                    }
                    if(firstCompletelyVisiblePlayableItem!=null){
                        firstCompletelyVisiblePlayableItem.start();
                    }
                    activeHolder = firstCompletelyVisiblePlayableItem;
                }
            }
        }
    };

    public ListPlayAdapter(Context context) {
        attachedHolders = new HashSet<>();
    }

    @Nullable
    private ListPlayItemViewHolder findFirstCompletelyVisiblePlayableItem(LinearLayoutManager layoutManager) {
        int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();

        int minPosition = -1;
        ListPlayItemViewHolder findItem = null;
        for (ListPlayItemViewHolder holder : attachedHolders) {
            int position = holder.getAdapterPosition();
            if (position < firstCompletelyVisibleItemPosition || firstCompletelyVisibleItemPosition > lastCompletelyVisibleItemPosition) {
                continue;
            }
            if (findItem == null) {
                findItem = holder;
                continue;
            }
            if (holder.getAdapterPosition() < findItem.getAdapterPosition()) {
                findItem = holder;
            }
        }
        return findItem;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof ListPlayItemViewHolder) {
            ListPlayItemViewHolder listPlayItemViewHolder = (ListPlayItemViewHolder) holder;
            attachedHolders.add(listPlayItemViewHolder);
        }

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof ListPlayItemViewHolder) {
            ListPlayItemViewHolder listPlayItemViewHolder = (ListPlayItemViewHolder) holder;
            attachedHolders.add(listPlayItemViewHolder);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

//        player = new FuPlayerManager(activity, new FuExoPlayerFactory(recyclerView.getContext()));
//
//        ScreenRotationHelper screenRotationHelper = new ScreenRotationHelper(activity);
//        screenRotationHelper.setDisableInPlayerStateEnd(true);
//        screenRotationHelper.setDisableInPlayerStateError(false);
//        screenRotationHelper.setToggleToPortraitInDisable(true);
//        screenRotationHelper.setEnablePortraitFullScreen(true);
//        screenRotationHelper.setAutoRotationMode(ScreenRotationHelper.AUTO_ROTATION_MODE_SYSTEM);
//
//        player.setScreenRotation(screenRotationHelper);
//
//        screenRotationHelper.setOnScreenChangedListener(this);
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

//        player.onDestroy();

        recyclerView.removeOnScrollListener(scrollListener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListPlayItemViewHolder(new ListPlayItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        Log.e("eeee", "onBindViewHolder");
        if (holder instanceof ListPlayItemViewHolder) {
            ListPlayItemViewHolder listPlayItemViewHolder = (ListPlayItemViewHolder) holder;

            map.put(position, listPlayItemViewHolder);
//            if (position == 4) {
//                listPlayItemViewHolder.start();
//                currentPlayVH = listPlayItemViewHolder;
//            }


//            if (!payloads.isEmpty()&& payloads.get(0) instanceof Integer) {
//                Integer payloadId = (Integer) payloads.get(0);
//                if (payloadId.intValue() == 0) {
//                    if (currentPlayVH!=null){
//                        currentPlayVH.setPlayer(null);
//                    }
//                    currentPlayVH=listPlayItemViewHolder;
//                    player.setMediaItem(MediaItem.fromUri("https://wsvod.gzstv.com/uploads/media/gzxwlb/1106jinbohuiVA1.mp4"));
//                    player.prepare();
//                    player.play();
//                    currentPlayVH.setPlayer(player);
//                    return;
//                }
//            }


        }
    }

    @Override
    public int getItemCount() {
        return 30;
    }
}
