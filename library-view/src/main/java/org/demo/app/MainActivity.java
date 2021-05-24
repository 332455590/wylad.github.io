package org.demo.app;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.demo.R;
import org.demo.banner.BannerActivity;
import org.demo.fullvideo.FullVideoActivity;
import org.demo.inter.InterActivity;
import org.demo.native_draw.NativeDrawVideoListActivity;
import org.demo.native_patch.NativePatchVideoActivity;
import org.demo.native_template.NativeExpressRecyclerViewActivity;
import org.demo.native_template.NativeExpressSingleActivity;
import org.demo.other.HelpActivity;
import org.demo.reward.RewardActivity;
import org.demo.splash.SplashMenuLoadAfterShowActivity;
import org.demo.splash.SplashMenuLoadAndShowActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    private String menu;

    private List<Map<String, Serializable>> menuList;

    private SimpleAdapter listAdapter;


    public static void action(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menu = getIntent().getStringExtra("menu");
        if (menu == null || menu.isEmpty()) {
            menu = getMenuMainActivities;
        }
        menuList = allActivityMap.get(menu);

        listAdapter = new SimpleAdapter(this, menuList, R.layout.list_item_main, new String[]{"title"}, new int[]{R.id.text1});
        getListView().setAdapter(listAdapter);
    }

    public static List<Map<String, Serializable>> menuMainList = new ArrayList<Map<String, Serializable>>() {
        {
            add(new HashMap<String, Serializable>() {{
                put("title", "引力广告 v1.100.0");
            }});
            add(new HashMap<String, Serializable>() {{
                put("title", "开屏");
                put("desc", "开屏广告以App启动作为曝光时机，提供5s的可感知广告展示。用户可以点击广告跳转到目标页面；或者点击右上角的“跳过”按钮，跳转到app内容首页");
                put("class", MainActivity.class);
                put("clickMenu", getMenuSplash);
            }});
            add(new HashMap<String, Serializable>() {{
                put("title", "Interstitial 插屏广告");
                put("desc", "在应用开启、暂停、退出时以半屏或全屏的形式弹出，展示时机巧妙避开用户对应用的正常体验。");
                put("class", InterActivity.class);
            }});
            add(new HashMap<String, Serializable>() {{
                put("title", "全屏视频广告~横/竖");
                put("desc", "对应的是优量汇的插屏全屏视频和穿山甲的全屏视频广告。全屏视频广告和激励视频广告比较像。差别在于全屏视频广告不需要完整的看完视频，就可以关闭广告。");
                put("class", FullVideoActivity.class);
            }});
            add(new HashMap<String, Serializable>() {{
                put("title", "Banner 横幅");
                put("desc", "Banner广告(横幅广告)位于app顶部、中部、底部任意一处，横向贯穿整个app页面；当用户与app互动时，Banner广告会停留在屏幕上，并可在一段时间后自动刷新");
                put("class", BannerActivity.class);
            }});
            add(new HashMap<String, Serializable>() {{
                put("title", "信息流 -> 原生模板渲染");
                put("desc", "相比于Banner广告、插屏广告等，原生广告提供了更加灵活、多样化的广告样式选择, 原生广告自渲染方式支持开发者自由拼合这些素材，最大程度的满足开发需求；");
                put("class", MainActivity.class);
                put("clickMenu", menuNativeExpress);
            }});
            add(new HashMap<String, Serializable>() {{
                put("title", "小视频");
                put("desc", "原生广告自渲染方式支持开发者自由拼合这些素材，最大程度的满足开发需求；与原生广告（模板方式）相比，自渲染方式更加自由灵活，开发者可以使用其为开发者的应用打造自定义的布局样式");
                put("class", NativeDrawVideoListActivity.class);
            }});
            add(new HashMap<String, Serializable>() {{
                put("title", "激励视频广告~横/竖");
                put("desc", "激励视频广告是指将短视频融入到app场景当中，成为app“任务”之一，用户观看短视频广告后可以得到一些应用内奖励");
                put("class", RewardActivity.class);
            }});
            add(new HashMap<String, Serializable>() {{
                put("title", "视频贴片广告~横/竖");
                put("desc", "贴片广告是常用于视频组件，用于给视频贴片，在视频预加载，暂停或结束时使用");
                put("class", NativePatchVideoActivity.class);
            }});
            add(new HashMap<String, Serializable>() {{
                put("title", "采坑指南");
                put("desc", "持续更新...");
                put("class", HelpActivity.class);
            }});
        }
    };


    public static List<Map<String, Serializable>> menuSplashList = new ArrayList<Map<String, Serializable>>() {
        {
            add(new HashMap<String, Serializable>() {{
                put("title", "请求并展示");
                put("desc", "请求成功后立即自动展示");
                put("class", SplashMenuLoadAndShowActivity.class);
            }});

            add(new HashMap<String, Serializable>() {
                {
                    put("title", "请求和展示分开");
                    put("desc", "请求成功后需手动调用展示。可实现预加载");
                    put("class", SplashMenuLoadAfterShowActivity.class);
                }
            });
        }
    };


    public static List<Map<String, Serializable>> menuNativeExpressList = new ArrayList<Map<String, Serializable>>() {
        {
            add(new HashMap<String, Serializable>() {
                {
                    put("title", "原生模板渲染");
                    put("desc", "简单用法");
                    put("class", NativeExpressSingleActivity.class);
                }
            });
            add(new HashMap<String, Serializable>() {
                {
                    put("title", "原生模板渲染 列表");
                    put("desc", "在RecyclerView中的用法");
                    put("class", NativeExpressRecyclerViewActivity.class);
                }
            });
        }
    };

    public final static String getMenuMainActivities = "menuMain";
    private final static String getMenuSplash = "menuSplash";// 开屏广告
    private final static String menuNativeExpress = "menuNativeExpress";

    public static Map<String, List<Map<String, Serializable>>> allActivityMap = new HashMap<String, List<Map<String, Serializable>>>() {
        {
            put(getMenuMainActivities, menuMainList);
            put(getMenuSplash, menuSplashList);
            put(menuNativeExpress, menuNativeExpressList);
        }
    };

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (menuList == null) return;
        Map<String, Serializable> map = menuList.get(position);
        Serializable classStr = map.get("class");
        if (classStr == null) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) classStr);
        intent.putExtra("menu", map.get("clickMenu"));
        startActivity(intent);
    }


    private long lastClickTimeLong = 0L;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastClickTimeLong > 1000 && menu == getMenuMainActivities) {
            lastClickTimeLong = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再点一次退出", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }
}