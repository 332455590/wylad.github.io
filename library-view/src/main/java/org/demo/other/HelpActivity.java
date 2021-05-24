package org.demo.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.demo.R;

public class HelpActivity extends Activity {


    public static void action(Context context) {
        context.startActivity(new Intent(context, HelpActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        String txt = " 广告法要求所有广告必须有广告的标示，否则会有风险。\n" +
                "\n" +
                "                -------------------------------------------\n" +
                "\n" +
                "                请求到的广告要尽量展示\n" +
                "                    ↓\n" +
                "                提高展示率、点击率\n" +
                "                    ↓\n" +
                "                提高 ecpm\n" +
                "                    ↓\n" +
                "                提高收入\n" +
                "\n" +
                "                -------------------------------------------\n" +
                "\n" +
                "                如果开发的时候出现错误码, 请在官网错误码列表查找解决方案, 如果还是解决不了, 可以在官网找到我们的客服联系\n" +
                "                注意事项\n" +
                "                1. SDK版本低；使用的sdk版本过低，升级到平台最新版本sdk。\n" +
                "                2. 广告位代码区使用错误；创建的代码位类型是模板渲染，但是请求方法是模板渲染的方法。\n" +
                "\n" +
                "                -------------------------------------------\n" +
                "\n" +
                " 官网地址: https://www.wyl.cn/welcome\n" +
                "\n" +
                "                有时候广告请求太频繁，触发了防刷量机制，可以等一会或者一个小时再试".trim();
        ((TextView) findViewById(R.id.tvText)).setText(txt);
    }

}
