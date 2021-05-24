package org.demo.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dsp.librarycore.custom.skip.SkipView2;
import com.dsp.librarycore.helper.AdHelperSplashPart;
import com.dsp.librarycore.listener.WjSplashListener;

import org.demo.R;
import org.demo.other.ErrUtil;

public class SplashMenuLoadAfterShowActivity extends Activity {

    private Button btnLoad, btnShow;

    private String ad_place_id = "ad_splash_0001";
    private AdHelperSplashPart adHelperSplashPart;
    private FrameLayout adContainer;


    public static void action(Context context) {
        context.startActivity(new Intent(context, SplashMenuLoadAfterShowActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_after);

        adContainer = findViewById(R.id.adContainer);

        btnLoad = findViewById(R.id.btnLoad);
        btnShow = findViewById(R.id.btnShow);

        btnLoad.setOnClickListener(v -> {
            //请求广告
            initAd();
        });
        btnShow.setOnClickListener(v -> {
            //展示广告
            showSplashAd();
        });

    }

    /**
     * 请求开屏广告
     */
    private void initAd() {
        /**
         * listener: 非必传。如果你不需要监听结果可以不传或传空。各个回调方法也可以选择性添加
         */
        adHelperSplashPart = new AdHelperSplashPart(this, ad_place_id, new WjSplashListener() {

            @Override
            public void onAdStartRequest() {
            }

            @Override
            public void onAdLoaded() {
                ErrUtil.showToast(SplashMenuLoadAfterShowActivity.this, "请求成功了");
                btnShow.setEnabled(true);
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdExposure() {
            }

            @Override
            public void onAdFailed(int code, String failedMsg) {

            }

            @Override
            public void onAdFailedAll(String failedMsg) {
                actionHome(1000);
            }

            @Override
            public void onAdDismissed() {
                actionHome(0);
            }
        });
        adHelperSplashPart.loadOnly(adContainer, new SkipView2(), 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adHelperSplashPart != null) {
            adHelperSplashPart.destroy();
        }
    }

    /**
     * 展示开屏广告
     */
    private void showSplashAd() {
        //展示广告并返回是否展示成功
        boolean showAdIsSuccess = false;
        if (adHelperSplashPart != null) {
            showAdIsSuccess = adHelperSplashPart.show();
        }
        //如果没有展示成功就直接跳走
        if (!showAdIsSuccess) {
            actionHome(1000);
        }
    }

    //不能手动返回
//    @Override
//    public void onBackPressed() {
//    }

    private void actionHome(long delayMillis) {
        adContainer.postDelayed(() -> {
            //在这里跳转到 Home 主界面
            finish();
        }, delayMillis);
    }

}
