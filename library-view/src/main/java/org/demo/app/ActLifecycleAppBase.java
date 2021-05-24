package org.demo.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.multidex.MultiDexApplication;

import org.demo.splash.SplashMenuLoadAndShowActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class ActLifecycleAppBase extends MultiDexApplication {

    //保存处于活跃状态的 Activity 个数
    private AtomicInteger mActivityCount = new AtomicInteger(0);

    //应用退到后台的时间戳
    private long mAppStopTimeMillis = 0L;


    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStarted(Activity activity) {
                if (activity == null) return;

                //热启动 && 应用退到后台时间超过10s
                if (mActivityCount.get() == 0 && System.currentTimeMillis() - mAppStopTimeMillis > 15 * 1000
                        && !(activity.getClass().getName().contains("Splash"))) {
                    SplashMenuLoadAndShowActivity.action(activity);
                }

                //+1
                mActivityCount.getAndAdd(1);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (activity == null) return;

                //-1
                mActivityCount.getAndDecrement();

                //退到后台，记录时间
                if (mActivityCount.get() == 0) {
                    mAppStopTimeMillis = System.currentTimeMillis();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }
        });
    }

}
