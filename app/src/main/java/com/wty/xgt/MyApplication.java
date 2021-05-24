package com.wty.xgt;


import com.dsp.librarycore.AdSdkConfig;
import com.dsp.librarycore.db.AdItemEntity;
import com.dsp.librarycore.listener.WjAllAdListener;

import org.demo.app.ActLifecycleAppBase;


public class MyApplication extends ActLifecycleAppBase {


    @Override
    public void onCreate() {
        super.onCreate();

        AdSdkConfig.init(this, "wyl_1321321");

        AdSdkConfig.sdkPrintLogEnable = true;

        AdSdkConfig.allAdListener = new WjAllAdListener() {
            @Override
            public void onAdStartRequest(AdItemEntity itemEntity) {
            }

            @Override
            public void onAdFailed(AdItemEntity itemEntity, int code, String failedMsg) {
            }

            @Override
            public void onAdLoaded(AdItemEntity itemEntity) {
            }
        };
    }

}
