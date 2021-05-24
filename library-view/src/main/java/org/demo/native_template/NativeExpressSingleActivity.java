package org.demo.native_template;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.dsp.librarycore.helper.AdHelperNativeTemplateExpress;
import com.dsp.librarycore.listener.WjNativeListener;
import com.dsp.librarycore.listener.WjNativeViewListener;

import org.demo.R;
import org.demo.other.ErrUtil;

import java.util.List;

/**
 * 信息流   单个样式显示
 */
public class NativeExpressSingleActivity extends Activity {

    private Object mAdObject = null;

    private ViewGroup adContainer;

    private String ad_place_id = "ad__6073e9b1cc50d";

    private AdHelperNativeTemplateExpress adHelperNativeExpress = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_express_simple);

        adContainer = findViewById(R.id.adContainer);

        findViewById(R.id.btnRequest).setOnClickListener(v -> requestAd());

        findViewById(R.id.btnShow).setOnClickListener(v -> showAd());
    }

    private void requestAd() {
        adHelperNativeExpress = new AdHelperNativeTemplateExpress(this, ad_place_id, new WjNativeListener() {
            @Override
            public <T> void onAdLoaded(List<T> adList) {
                ErrUtil.showToast(NativeExpressSingleActivity.this,"请求成功了");
                mAdObject = adList.get(0);
            }

            @Override
            public void onAdStartRequest() {
            }

            @Override
            public void onAdFailed(int code, String failedMsg) {

            }

            @Override
            public void onAdFailedAll(String failedMsg) {
            }

        }, new WjNativeViewListener() {
            @Override
            public void onAdShow() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdRenderSuccess() {

            }

            @Override
            public void onAdRenderFailed() {

            }

            @Override
            public void onAdClosed() {
                if (adContainer != null) adContainer.removeAllViews();
            }

            @Override
            public void onAdSelected(Object o) {
                if (adContainer != null) adContainer.removeAllViews();
            }

            @Override
            public void onAdStatusChanged() {

            }
        });
        adHelperNativeExpress.load(1);
    }

    private void showAd() {
        adHelperNativeExpress.show(mAdObject, adContainer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adHelperNativeExpress != null) adHelperNativeExpress.destroy();
    }

}
