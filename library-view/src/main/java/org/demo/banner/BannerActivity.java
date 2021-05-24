package org.demo.banner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.dsp.librarycore.helper.AdHelperBanner;
import com.dsp.librarycore.listener.WjBannerListener;

import org.demo.R;
import org.demo.other.ErrUtil;

/**
 * Banner尺寸多样  不要固定父类view容器的尺寸
 */
public class BannerActivity extends Activity {

    private String ad_place_id = "ad__6073becb0ac68";
    private AdHelperBanner adHelperBanner01;
    private AdHelperBanner adHelperBanner02;
    private AdHelperBanner adHelperBanner03;


    public static void action(Context context) {
        context.startActivity(new Intent(context, BannerActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ViewGroup adContainer01 = findViewById(R.id.adContainer01);
        findViewById(R.id.btnRequestShow01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBannerAd01(ad_place_id, adContainer01);
            }
        });

        ViewGroup adContainer02 = findViewById(R.id.adContainer02);
        findViewById(R.id.btnRequestShow02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBannerAd02(ad_place_id, adContainer02);
            }
        });

        ViewGroup adContainer03 = findViewById(R.id.adContainer03);
        findViewById(R.id.btnRequestShow03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBannerAd03(ad_place_id, adContainer03);
            }
        });
    }

    private void showBannerAd01(String ad_place_id, ViewGroup adContainer) {
        adHelperBanner01 = new AdHelperBanner(this, ad_place_id, new WjBannerListener() {
            @Override
            public void onAdStartRequest() {
            }

            @Override
            public void onAdLoaded() {
                ErrUtil.showToast(BannerActivity.this, "banner请求成功了01");
            }

            @Override
            public void onAdFailed(int code, String failedMsg) {
            }

            @Override
            public void onAdFailedAll(String failedMsg) {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdExpose() {
                //广告展示曝光的回调；由于 Banner 广告存在自动刷新功能，所以曝光会回调多次，每次刷新都会回调
            }

            @Override
            public void onAdClose() {
                //广告被关闭的回调
            }
        });

        adContainer.removeAllViews();
        adHelperBanner01.show(adContainer);
    }

    private void showBannerAd02(String ad_place_id, ViewGroup adContainer) {
        adHelperBanner02 = new AdHelperBanner(this, ad_place_id, new WjBannerListener() {
            @Override
            public void onAdStartRequest() {
            }

            @Override
            public void onAdLoaded() {
                ErrUtil.showToast(BannerActivity.this, "banner请求成功了02");
            }

            @Override
            public void onAdFailed(int code, String failedMsg) {
            }

            @Override
            public void onAdFailedAll(String failedMsg) {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdExpose() {
                //广告展示曝光的回调；由于 Banner 广告存在自动刷新功能，所以曝光会回调多次，每次刷新都会回调
            }

            @Override
            public void onAdClose() {
                //广告被关闭的回调
            }
        });

        adContainer.removeAllViews();
        adHelperBanner02.show(adContainer);
    }

    private void showBannerAd03(String ad_place_id, ViewGroup adContainer) {
        adHelperBanner03 = new AdHelperBanner(this, ad_place_id, new WjBannerListener() {
            @Override
            public void onAdStartRequest() {
            }

            @Override
            public void onAdLoaded() {
                ErrUtil.showToast(BannerActivity.this, "banner请求成功了03");
            }

            @Override
            public void onAdFailed(int code, String failedMsg) {
            }

            @Override
            public void onAdFailedAll(String failedMsg) {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdExpose() {
                //广告展示曝光的回调；由于 Banner 广告存在自动刷新功能，所以曝光会回调多次，每次刷新都会回调
            }

            @Override
            public void onAdClose() {
                //广告被关闭的回调
            }
        });

        adContainer.removeAllViews();
        adHelperBanner03.show(adContainer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁，避免内存泄漏
        if (adHelperBanner01 != null) {
            adHelperBanner01.destroy();
        }
        if (adHelperBanner02 != null) {
            adHelperBanner02.destroy();
        }
        if (adHelperBanner03 != null) {
            adHelperBanner03.destroy();
        }
    }
}
