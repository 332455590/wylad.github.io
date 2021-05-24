package org.demo.fullvideo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.dsp.librarycore.helper.AdHelperFullVideo;
import com.dsp.librarycore.listener.WjFullVideoListener;

import org.demo.R;
import org.demo.other.ErrUtil;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * 全屏视频流
 */
public class FullVideoActivity extends Activity {

    private String ad_place_id = "";
    private String ad_place_id1 = "ad_fullVideo_0001";
    private String ad_place_id2 = "ad_60a219d6e30f2"; //  横
    private AdHelperFullVideo adHelperFullVideo;
    private ViewGroup adContainer;

    public static void action(Context context) {
        context.startActivity(new Intent(context, FullVideoActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);
        adContainer = findViewById(R.id.adContainer);

        findViewById(R.id.btnChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentOrientation = getResources().getConfiguration().orientation;
                if (currentOrientation == ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if (currentOrientation == ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });


        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始请求广告
                int currentOrientation = getResources().getConfiguration().orientation;
                if (currentOrientation == ORIENTATION_PORTRAIT) {
                    ad_place_id = ad_place_id1;
                } else if (currentOrientation == ORIENTATION_LANDSCAPE) {
                    ad_place_id = ad_place_id2;
                }
                adHelperFullVideo.load(ad_place_id);
            }
        });

        findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onAdLoaded 回调之后才能展示
                adHelperFullVideo.show();
            }
        });

        initAd();
    }

    private void initAd() {
        adHelperFullVideo = new AdHelperFullVideo(this, new WjFullVideoListener() {
            @Override
            public void onAdStartRequest() {
                //在开始请求之前会回调此方法，失败切换的情况会回调多次
            }


            @Override
            public void onAdLoaded() {
                //广告请求成功的回调，每次请求只回调一次
                ErrUtil.showToast(FullVideoActivity.this, "请求成功了");
            }

            @Override
            public void onAdFailed(int code, String failedMsg) {
                //请求失败的回调，失败切换的情况会回调多次
            }

            @Override
            public void onAdFailedAll(String failedMsg) {
                //所有配置的广告商都请求失败了，只有在全部失败之后会回调一次
            }

            @Override
            public void onAdClicked() {
                //点击广告的回调
            }

            @Override
            public void onAdShow() {
                //广告展示展示的回调
            }

            @Override
            public void onAdVideoCached() {
                //视频缓存完成的回调
            }

            @Override
            public void onAdClose() {
                //广告被关闭的回调
            }

            @Override
            public void onAdVideoComplete() {
                //广告播放完成的回调
            }
        });

    }

}
