package org.demo.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.dsp.librarycore.AdSdkConfig;
import com.dsp.librarycore.custom.skip.SkipView2;
import com.dsp.librarycore.helper.AdHelperSplashPart;
import com.dsp.librarycore.listener.WjSplashListener;

import org.demo.R;
import org.demo.other.ErrUtil;

/**
 * 开屏：请求完广告，直接显示
 */
public class SplashMenuLoadAndShowActivity extends Activity {

    private String ad_place_id = "ad_splash_0001";
    private AdHelperSplashPart adHelperSplash;
    private ViewGroup adContainer;


    public static void action(Context context) {
        context.startActivity(new Intent(context, SplashMenuLoadAndShowActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        adContainer = findViewById(R.id.adContainer);

        //开始请求开屏广告
        requestSplashAd();
    }

    /**
     * 请求开屏广告
     */
    private void requestSplashAd() {

        AdSdkConfig.setDelayRequestMaxFetch(2000);


        adHelperSplash = new AdHelperSplashPart(this, ad_place_id, new WjSplashListener() {

            @Override
            public void onAdStartRequest() {
            }

            @Override
            public void onAdLoaded() {
                ErrUtil.showToast(SplashMenuLoadAndShowActivity.this, "请求成功了");
                if (adHelperSplash != null && !SplashMenuLoadAndShowActivity.this.isFinishing()) {
                    adHelperSplash.show();
                }
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
        adHelperSplash.loadOnly(adContainer, new SkipView2(), 2000);
    }

    private void actionHome(long delayMillis) {
        adContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                //在这里只关闭当前页即可
                finish();
            }
        }, delayMillis);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdSdkConfig.setDelayRequestMaxFetch(5000);
        if (adHelperSplash != null) {
            adHelperSplash.destroy();
        }
    }
}
