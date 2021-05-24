package org.demo.native_draw;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.dsp.librarycore.helper.AdHelperNativeDrawMovie;
import com.dsp.librarycore.listener.WjNativeListener;
import com.dsp.librarycore.listener.WjNativeViewListener;

import org.demo.R;
import org.demo.other.ContentDataEntity;
import org.demo.other.ErrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 像抖音一样的展现形式
 * 竖屏视频信息流
 */
public class NativeDrawVideoListActivity extends Activity {

    public static final String TAG = "FeedDraw:";

    private boolean mIsLoading = true;

    private int[] mVideoIds = new int[]{R.raw.v1, R.raw.v2, R.raw.v3, R.raw.v4, R.raw.v5};
    private int[] mImageIds = new int[]{R.raw.p1, R.raw.p2, R.raw.p3, R.raw.p4, R.raw.p5};

    private RecyclerView recyclerView;

    private List<Object> allList = new ArrayList<>();

    private String ad_id = "ad__6073e9c026f27";
    private AdHelperNativeDrawMovie drawFeed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//        } catch (Throwable ignore) {
//        }
        setContentView(R.layout.activity_native_draw_video);

        initAd();

        initViewPagerLayoutManager();
        recyclerView.setAdapter(new NativeDrawAdapter(NativeDrawVideoListActivity.this, allList, drawFeed));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!mIsLoading && newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)) {
                    mIsLoading = true;
                    if (drawFeed != null) {
                        drawFeed.load(3);
                    }
                }
            }
        });
        drawFeed.load(3);
    }

    private void initAd() {
        drawFeed = new AdHelperNativeDrawMovie(this, ad_id, new WjNativeListener() {
            @Override
            public <T> void onAdLoaded(List<T> adList) {
                ErrUtil.showToast(NativeDrawVideoListActivity.this, "请求成功了");

                mIsLoading = false;

                List<Object> list = mergeContentAd(getContentData(), adList);
                allList.addAll(list);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onAdStartRequest() {

            }

            @Override
            public void onAdFailed(int code, String failedMsg) {

            }

            @Override
            public void onAdFailedAll(String failedMsg) {

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

            }

            @Override
            public void onAdStatusChanged() {

            }
        });
    }

    private void initViewPagerLayoutManager() {
        recyclerView = findViewById(R.id.recycler_view);
        ViewPagerLayoutManager mLayoutManager = new ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL);
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                if (allList.get(0) instanceof ContentDataEntity) {
                    play();
                }
                mCurrentPage = 0;
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (allList.isEmpty() || allList.size() < position) {
                    return;
                }
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);
                if (allList.size() < position) {
                    return;
                }
                if (allList.get(position) instanceof ContentDataEntity) {
                    releaseVideo(isNext ? 0 : 1);
                }

            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (allList.size() < position) {
                    return;
                }
                Log.e(TAG, "选中位置:" + position + "  是否是滑动到底部:" + isBottom);
                if (allList.get(position) instanceof ContentDataEntity) {
                    play();
                }
                mCurrentPage = position;
            }
        });
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void play() {
        View itemView = recyclerView.getChildAt(0);
        if (itemView == null) {
            return;
        }
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final View coverImage = itemView.findViewById(R.id.cover_image);
        if (videoView != null) {
            videoView.start();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        Log.d(TAG, "onInfo");
                        mp.setLooping(true);
                        coverImage.animate().alpha(0).setDuration(200).start();
                        return false;
                    }
                });
            }
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared");
                }
            });
        }
    }

    private void releaseVideo(int index) {
        View itemView = recyclerView.getChildAt(index);
        if (itemView != null) {
            final VideoView videoView = itemView.findViewById(R.id.video_view);
            final View coverImage = itemView.findViewById(R.id.cover_image);
            if (videoView != null) {
                videoView.stopPlayback();
            }
            if (coverImage != null) {
                coverImage.animate().alpha(1).start();
            }
        }
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
                nextAdPosition += 1;
            }
        }
        return multiItemList;
    }

    /**
     * 模拟真实的内容数据List
     */

    private int index = 0;

    private List<ContentDataEntity> getContentData() {
        List<ContentDataEntity> contentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String title = "正文内容序号：" + index++;
            contentList.add(new ContentDataEntity(title,
                    "android.resource://" + getPackageName() + "/" + mVideoIds[i % mVideoIds.length],
                    "android.resource://" + getPackageName() + "/" + mImageIds[i % mImageIds.length]));

        }
        return contentList;
    }

    private class ViewPagerLayoutManager extends LinearLayoutManager {
        private PagerSnapHelper mPagerSnapHelper;
        private OnViewPagerListener mOnViewPagerListener;
        private RecyclerView mRecyclerView;
        private int mDeltaY;

        private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
            public void onChildViewAttachedToWindow(View view) {
                if (mOnViewPagerListener != null && getChildCount() == 1) {
                    mOnViewPagerListener.onInitComplete();
                }
            }

            public void onChildViewDetachedFromWindow(View view) {
                if (mDeltaY >= 0) {
                    if (mOnViewPagerListener != null) {
                        mOnViewPagerListener.onPageRelease(true, getPosition(view));
                    }
                } else if (mOnViewPagerListener != null) {
                    mOnViewPagerListener.onPageRelease(false, getPosition(view));
                }
            }
        };

        public ViewPagerLayoutManager(Context context, int orientation) {
            super(context, orientation, false);
            mPagerSnapHelper = new PagerSnapHelper();
        }

        public void onAttachedToWindow(RecyclerView view) {
            super.onAttachedToWindow(view);
            mPagerSnapHelper.attachToRecyclerView(view);
            mRecyclerView = view;
            mRecyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
        }

        public void onScrollStateChanged(int state) {
            if (state == RecyclerView.SCROLL_STATE_IDLE) {
                View curView = mPagerSnapHelper.findSnapView(this);
                if (curView == null) {
                    return;
                }
                int curPos = getPosition(curView);
                if (mOnViewPagerListener != null && getChildCount() == 1) {
                    mOnViewPagerListener.onPageSelected(curPos, curPos == getItemCount() - 1);
                }
            }
        }

        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
            mDeltaY = dy;
            return super.scrollVerticallyBy(dy, recycler, state);
        }

        public void setOnViewPagerListener(OnViewPagerListener listener) {
            mOnViewPagerListener = listener;
        }
    }


    private int mCurrentPage = -1;
    private int mVideoViewCurrentPosition = -1;
    private VideoView mCurrentVideoView;
    private boolean videoIsPaused = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (drawFeed != null) {
            drawFeed.resumeAd();
        }
        if (videoIsPaused) {
            mCurrentVideoView.seekTo(mVideoViewCurrentPosition);
            mCurrentVideoView.start();
            videoIsPaused = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (drawFeed != null) {
            drawFeed.pauseAd();
        }
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            NativeDrawAdapter adapter = (NativeDrawAdapter) recyclerView.getAdapter();
            if (adapter.getItemViewType(mCurrentPage) == NativeDrawAdapter.ITEM_VIEW_TYPE_CONTENT) {
                ViewPagerLayoutManager mLayoutManager = (ViewPagerLayoutManager) recyclerView.getLayoutManager();
                if (mLayoutManager.findViewByPosition(mCurrentPage) == null) {
                    return;
                }
                mCurrentVideoView = mLayoutManager.findViewByPosition(mCurrentPage)
                        .findViewById(R.id.video_view);
                mVideoViewCurrentPosition = mCurrentVideoView.getCurrentPosition();
                mCurrentVideoView.pause();
                videoIsPaused = true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (drawFeed != null) {
            drawFeed.destroy();
        }
    }
}
