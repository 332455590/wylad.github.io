package org.demo.native_template;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dsp.librarycore.helper.AdHelperNativeTemplateExpress;

import org.demo.R;
import org.demo.other.ContentDataEntity;

import java.util.List;

public class NativeExpressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private final int ITEM_VIEW_TYPE_AD = 0;
    private final int ITEM_VIEW_TYPE_CONTENT = 1;

    private List<Object> mList;
    private Activity activity;

    private AdHelperNativeTemplateExpress adHelperNativeExpressRv; // NativeTemplateExpressView

    public NativeExpressAdapter(Activity activity, List<Object> mList, AdHelperNativeTemplateExpress adHelperNativeExpressRv) {
        this.mList = mList;
        this.activity = activity;
        this.adHelperNativeExpressRv = adHelperNativeExpressRv;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_CONTENT) {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_native_content, parent, false));
        } else {
            return new AdViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_native_ad, parent, false));
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
            contentViewHolder.textView.setText(contentDataEntity.getTitle());
            Glide.with(contentViewHolder.imageView.getContext()).load(contentDataEntity.getImgUrl()).into(contentViewHolder.imageView);
        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_AD) {
            AdViewHolder adViewHolder = (AdViewHolder) holder;
            adHelperNativeExpressRv.show(mList.get(position), adViewHolder.adContainer);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position) instanceof ContentDataEntity) {
            return ITEM_VIEW_TYPE_CONTENT;
        } else {
            return ITEM_VIEW_TYPE_AD;
        }
    }

    class AdViewHolder extends RecyclerView.ViewHolder {
        ViewGroup adContainer;

        public AdViewHolder(View itemView) {
            super(itemView);
            adContainer = itemView.findViewById(R.id.adContainer);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.txt);
        }
    }

}
