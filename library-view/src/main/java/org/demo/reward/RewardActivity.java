package org.demo.reward;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.dsp.librarycore.entity.AdRewardTradeBean;
import com.dsp.librarycore.helper.AdHelperReward;
import com.dsp.librarycore.listener.WjRewardListener;

import org.demo.R;
import org.demo.other.ErrUtil;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * 激励视频
 */
public class RewardActivity extends Activity {

    private String ad_place_id;
    private String ad_place_id1 = "ad__6073e9ca26b86";
    private String ad_place_id2 = "ad_60a219ba5236c"; // 横屏
    private AdHelperReward adHelperReward;

    public static void action(Context context) {
        context.startActivity(new Intent(context, RewardActivity.class));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

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

        findViewById(R.id.btnRequest).setOnClickListener(v -> {
            //开始请求广告
            AdRewardTradeBean tradeBean = new AdRewardTradeBean();
            int currentOrientation = getResources().getConfiguration().orientation;
            if (currentOrientation == ORIENTATION_PORTRAIT) {
                ad_place_id = ad_place_id1;
                tradeBean.setR_uid("激励用户ID");
                tradeBean.setR_rname("激励奖励名称");
                tradeBean.setR_rnum("激励奖励数量");
                tradeBean.setR_ext("激励扩展参数");
            } else if (currentOrientation == ORIENTATION_LANDSCAPE) {
                ad_place_id = ad_place_id2;
                tradeBean.setR_uid("激励用户ID2");
                tradeBean.setR_rname("激励奖励名称2");
                tradeBean.setR_rnum("激励奖励数量2");
                tradeBean.setR_ext("激励扩展参数2");
            }
            adHelperReward.load(ad_place_id, tradeBean);
        });

        findViewById(R.id.btnShow).setOnClickListener(v -> {
            //onAdLoaded 回调之后才能展示
            adHelperReward.show();
        });
    }

    private void initAd() {
        adHelperReward = new AdHelperReward(this, new WjRewardListener() {
            @Override
            public void onAdStartRequest() {
            }

            @Override
            public void onAdLoaded() {
                ErrUtil.showToast(RewardActivity.this, "请求成功了");
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
            public void onAdShow() {
            }


            @Override
            public void onAdExpose() {
            }

            @Override
            public void onAdVideoComplete() {
            }

            @Override
            public void onAdVideoCached() {
            }

            @Override
            public void onAdRewardVerify() {
            }

            @Override
            public void onAdClose() {
            }

            @Override
            public void getTrade(AdRewardTradeBean adRewardTradeBean) {

            }
        });
    }

}
