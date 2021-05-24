package org.demo.native_template;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsp.librarycore.helper.AdHelperNativeTemplateExpress;
import com.dsp.librarycore.listener.WjNativeListener;
import com.dsp.librarycore.listener.WjNativeViewListener;

import org.demo.R;
import org.demo.other.ContentDataEntity;
import org.demo.other.ErrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NativeExpressRecyclerViewActivity extends Activity {

    private boolean mIsLoading = true;

    private final List<Object> allList = new ArrayList<>();
    private RecyclerView recyclerView;

    private final String ad_place_id = "ad__6073e9b1cc50d";
    private final int adCount = 3;
    private AdHelperNativeTemplateExpress adHelperNativeExpressRv = null;

    public static void action(Context context) {
        context.startActivity(new Intent(context, NativeExpressRecyclerViewActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_recyclerview);

        initAd();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NativeExpressAdapter(this, allList, adHelperNativeExpressRv));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滚动到底再次加载广告
                if (!mIsLoading && newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)) {
                    mIsLoading = true;
                    if (adHelperNativeExpressRv != null) {
                        adHelperNativeExpressRv.load(adCount);
                    }
                }
            }
        });

        adHelperNativeExpressRv.load(adCount);
    }

    /**
     * 请求广告List
     */
    private void initAd() {
        adHelperNativeExpressRv = new AdHelperNativeTemplateExpress(this, ad_place_id, new WjNativeListener() {
            @Override
            public <T> void onAdLoaded(List<T> adList) {
                ErrUtil.showToast(NativeExpressRecyclerViewActivity.this, "请求成功了");

                mIsLoading = false;

                List<Object> list = mergeContentAd(getContentData(), adList);
                allList.addAll(list);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onAdStartRequest() {
                //每个提供商请求之前都会回调
            }

            @Override
            public void onAdFailed(int code, String failedMsg) {
                //单个提供商请求失败
                mIsLoading = false;
            }

            @Override
            public void onAdFailedAll(String failedMsg) {
                mIsLoading = false;
            }
        }, new WjNativeViewListener() {
            @Override
            public void onAdShow() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdRenderSuccess() {

            }

            @Override
            public void onAdRenderFailed() {

            }

            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdSelected(Object o) {
                int position = allList.indexOf(o);
                allList.remove(position);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyItemRemoved(position);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyItemRangeChanged(0, allList.size() - 1);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onAdStatusChanged() {

            }
        });
    }

    private <T> List<Object> mergeContentAd(List<ContentDataEntity> contentList, List<T> adList) {

        int nextAdPosition = 0;
        int lastUseAdPosition = 0;

        List<Object> multiItemList = new ArrayList<>();
        for (int i = 0; i < contentList.size(); i++) {
            ContentDataEntity it = contentList.get(i);
            multiItemList.add(it);
            if (adList != null && !adList.isEmpty() && nextAdPosition == i) {
                if (lastUseAdPosition > adList.size() - 1) {
                    continue;
                }
                multiItemList.add(adList.get(lastUseAdPosition));
                lastUseAdPosition += 1;
                nextAdPosition += 5;
            }
        }
        return multiItemList;
    }

    private int index = 0;

    private List<ContentDataEntity> getContentData() {
        List<ContentDataEntity> contentList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String title = "正文内容序号：" + index++;
            contentList.add(new ContentDataEntity(title, "https://t8.baidu.com/it/u=2247852322,986532796&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590128472&t=657ec840a5c6c658430135ea8b1d35f0"));
        }
        return contentList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adHelperNativeExpressRv != null) adHelperNativeExpressRv.destroy();
    }

}
