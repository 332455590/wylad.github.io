package org.demo.native_draw;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.dsp.librarycore.custom.widget.FullScreenVideoView;
import com.dsp.librarycore.helper.AdHelperNativeDrawMovie;

import org.demo.R;
import org.demo.other.ContentDataEntity;

import java.util.List;

public class NativeDrawAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int ITEM_VIEW_TYPE_AD = 0;
    public final static int ITEM_VIEW_TYPE_CONTENT = 1;

    private List<Object> mList;

    private Activity activity;

    private AdHelperNativeDrawMovie drawFeed;

    public NativeDrawAdapter(Activity activity, List<Object> mList, AdHelperNativeDrawMovie drawFeed) {
        this.activity = activity;
        this.mList = mList;
        this.drawFeed = drawFeed;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_CONTENT) {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_full_screen_video_feed, parent, false));
        } else {
            return new AdViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_native_feed_draw_view, parent, false));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_CONTENT) {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            ContentDataEntity contentDataEntity = (ContentDataEntity) mList.get(position);
            contentViewHolder.video_view.setVideoURI(Uri.parse(contentDataEntity.getImgUrl()));
            contentViewHolder.coverImage.setImageURI(Uri.parse(contentDataEntity.getImgUrlRes()));
        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_AD) {
            AdViewHolder adViewHolder = (AdViewHolder) holder;
            drawFeed.show(mList.get(position), adViewHolder.adContainer);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.isEmpty()) {
            return 2;
        }
        return (mList.get(position) instanceof ContentDataEntity) ? ITEM_VIEW_TYPE_CONTENT : ITEM_VIEW_TYPE_AD;
    }

    class AdViewHolder extends RecyclerView.ViewHolder {

        ViewGroup adContainer;

        public AdViewHolder(View itemView) {
            super(itemView);
            adContainer = itemView.findViewById(R.id.adContainer);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        FullScreenVideoView video_view;
        ImageView coverImage;

        public ContentViewHolder(View itemView) {
            super(itemView);
            video_view = itemView.findViewById(R.id.video_view);
            coverImage = itemView.findViewById(R.id.cover_image);
        }
    }

}
