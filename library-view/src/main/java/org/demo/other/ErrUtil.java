package org.demo.other;

import android.content.Context;
import android.widget.Toast;

import com.qq.e.comm.util.StringUtil;

public class ErrUtil {

    public static void showToast(Context context, String msg) {
        if (!StringUtil.isEmpty(msg) && context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

}
