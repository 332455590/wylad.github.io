package org.demo.native_patch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dsp.librarycore.helper.AdHelperNativePatchExpress;
import com.dsp.librarycore.listener.WjPatchVideoListener;

import org.demo.R;
import org.demo.other.ErrUtil;

import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * 贴边视屏广告
 */
public class NativePatchVideoActivity extends Activity {

    private String ad_id = "ad_videoPreroll_0001"; // csj  Banner:945955794 template:946007739  feed:946086366
    private AdHelperNativePatchExpress adHelperNativePatch = null;
    private RelativeLayout adContainer;

    private boolean isCanClosed = false;

    public static void action(Context context) {
        context.startActivity(new Intent(context, NativePatchVideoActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_patch_view2);

        adContainer = findViewById(R.id.adContainer);

        findViewById(R.id.btnShow).setOnClickListener(v -> {
            adContainer.removeAllViews();
            adHelperNativePatch.show(adContainer, 15000);
        });

        findViewById(R.id.btnClose).setOnClickListener(v -> {
            adContainer.removeAllViews();
            adHelperNativePatch.close();
        });

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

        initAd();
    }

    /**
     * 请求广告
     */
    private void initAd() {
        adHelperNativePatch = new AdHelperNativePatchExpress(this, ad_id, new WjPatchVideoListener() {
            @Override
            public <T> void onAdLoaded(List<T> adList) {
                //广告请求成功的回调，每次请求只回调一次
                ErrUtil.showToast(NativePatchVideoActivity.this, "请求成功了");

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdShow(String type) {

            }

            @Override
            public void onAdVideoComplete() {
                isCanClosed = true;

            }

            @Override
            public void onAdClose() {
                adHelperNativePatch.close();
            }

            @Override
            public boolean onAdCloseJudge() {
                if (isCanClosed) {
                    return true;
                }
                Toast.makeText(NativePatchVideoActivity.this, "倒计时还没有到...", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onAdStartRequest() {

            }

            @Override
            public void onAdFailed(int code, String failedMsg) {
                //请求失败的回调，失败切换的情况会回调多次
            }

            @Override
            public void onAdFailedAll(String failedMsg) {
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (adHelperNativePatch != null) {
            adHelperNativePatch.onConfigurationChangedPatchVideo(newConfig.orientation);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getApplicationContext(), "横屏", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "竖屏", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adHelperNativePatch != null) {
            adHelperNativePatch.destroy();
        }
    }

}
