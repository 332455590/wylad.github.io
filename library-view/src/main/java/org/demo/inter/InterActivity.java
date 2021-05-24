package org.demo.inter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.dsp.librarycore.helper.AdHelperInter;
import com.dsp.librarycore.listener.WjInterListener;

import org.demo.R;
import org.demo.other.ErrUtil;

/**
 * 插屏广告
 */
public class InterActivity extends Activity {

    private String ad_place_id = "ad_interstitial_0001";
    private AdHelperInter adHelperInter = null;
    private ViewGroup adContainer;

    public void action(Context context) {
        context.startActivity(new Intent(context, InterActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter);
        adContainer = findViewById(R.id.adContainer);

        findViewById(R.id.btnRequest).setOnClickListener(v -> {
            //开始请求插屏广告
            adHelperInter.load();
        });

        findViewById(R.id.btnShow).setOnClickListener(v -> {
            //开始展示插屏广告，必须在 onAdLoaded 回调之后展示
            adHelperInter.show();
        });

        initAd();
    }

    private void initAd() {
        adHelperInter = new AdHelperInter(this, ad_place_id, new WjInterListener() {
            @Override
            public void onAdStartRequest() {
                //在开始请求之前会回调此方法，失败切换的情况会回调多次
            }

            @Override
            public void onAdLoaded() {
                //广告请求成功的回调，每次请求只回调一次
                ErrUtil.showToast(InterActivity.this, "请求成功了");
            }

            @Override
            public void onAdVideoCached() {

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
            public void onAdExpose() {
                //广告展示曝光的回调；由于 Banner 广告存在自动刷新功能，所以曝光会回调多次，每次刷新都会回调
            }

            @Override
            public void onAdClose() {
                //广告被关闭的回调
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adHelperInter != null) {
            adHelperInter.destroy();
        }
    }

}
