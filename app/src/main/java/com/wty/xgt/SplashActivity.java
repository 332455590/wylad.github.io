package com.wty.xgt;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dsp.librarycore.AdSdkConfig;
import com.dsp.librarycore.helper.AdHelperSplashShow;
import com.dsp.librarycore.listener.WjSplashListener;

import org.demo.app.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {

    private String ad_id = "ad_splash_0001";
    private AdHelperSplashShow adHelperSplash;
    private FrameLayout adContainer;


    /**
     * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
     * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
     * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值（单位ms）
     **/
    private int minSplashTimeWhenNoAD = 2000;
    private long fetchSplashADTime = 0;// 记录拉取广告的时间
    private boolean isJump = false;// 防止多次跳转

    private boolean isPermission = false;

    //是否强制跳转到主页面 cs
    private boolean mForceGoMain;

    public static void action(Context context) {
        context.startActivity(new Intent(context, SplashActivity.class));


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        adContainer = findViewById(R.id.adContainer);

        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        }
    }

    /**
     * 请求开屏广告
     */
    private void initAd() {
        fetchSplashADTime = System.currentTimeMillis();

        /**
         * 广告超时时间
         */
        AdSdkConfig.setDelayRequestMaxFetch(3000);

        adHelperSplash = new AdHelperSplashShow(this, "ad_splash_0001", new WjSplashListener() {

            @Override
            public void onAdStartRequest() {
            }

            @Override
            public void onAdLoaded() {
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
                long time = System.currentTimeMillis() - fetchSplashADTime;
                if (time < minSplashTimeWhenNoAD) {
                    actionHome(minSplashTimeWhenNoAD);
                } else {
                    actionHome(0);
                }
            }

            @Override
            public void onAdDismissed() {
                actionHome(0);
            }
        });
        adHelperSplash.show(adContainer);
    }

    //不能手动返回
    @Override
    public void onBackPressed() {
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("tag:", "11111");
        if (mForceGoMain && fetchSplashADTime != 0) {
            actionHome(0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mForceGoMain = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdSdkConfig.setDelayRequestMaxFetch(5000);
        if (adHelperSplash != null) {
            adHelperSplash.destroy();
        }
    }

    private void actionHome(long delayMillis) {
        if (isJump) {
            return;
        }
        isJump = true;
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //在这里跳转到 Home 主界面
                MainActivity.action(SplashActivity.this);
                finish();
            }
        }, delayMillis);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // 快手SDK所需相关权限，存储权限，此处配置作用于流量分配功能，关于流量分配，详情请咨询商务;如果您的APP不需要快手SDK的流量分配功能，则无需申请SD卡权限
        if (!(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        // 如果需要的权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            //开始请求开屏广告
            //开始请求开屏广告
            isPermission = true;
            initAd();
        } else {
            // 否则，建议请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            // 已经获取到权限了
            //开始请求开屏广告
            if (!isPermission) {
                initAd();
            }
        } else {
            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        }
    }

}
