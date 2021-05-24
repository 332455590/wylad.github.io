# 聚合广告业务

## Android 集成

> 注意: 本版本sdk只适用于中国地区,海外地区的版本在之后的版本中更新



### 更新版本

| 版本号 | 说明 |
| ------ | ---- |
|        |      |



## SDK集成

### 申请应用的AppId

请在微引力平台上创建好应用ID和广告位ID

### 方式一：导入aar及SDK依赖的jar包

将本SDK压缩包内的**wyl_sdk_XXX.aar**复制到`Application Module/libs`文件夹(没有的话须手动创建), 并将以下代码添加到您app的`build.gradle`中：

```groovy
repositories {
    flatDir {
        dirs 'libs'
    }
}
depedencies {
    compile(name: 'wyl_sdk_1.100.0', ext: 'aar')
}
```

### 运行环境配置

本SDK可运行于Android4.0 (API Level 14) 及以上版本。
`<uses-sdk android:minSdkVersion="14" android:targetSdkVersion="28" />`

**如果开发者声明targetSdkVersion到API 23以上，请确保调用本SDK的任何接口前，已经申请到了SDK要求的所有权限，否则SDK部分特性可能受限**

### 代码混淆

如果您需要使用proguard混淆代码，需确保不要混淆SDK的代码。 请在`proguard-rules.pro`文件(或其他混淆文件)尾部添加如下配置:

```
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View {*;}
-keep class * extends com.dsp.librarycore.litepal.crud.LitePalSupport {*;}
-keep class * extends com.dsp.librarycore.custom.template.BaseNativeTemplateExpress  {*;}
-keep class * extends com.dsp.librarycore.helper.BaseHelper {*;}
-keep class com.dsp.librarycore.custom.widget.FullScreenVideoView {*;}
-keep class com.dsp.librarycore.listener.* {*;}
-keep class com.dsp.librarycore.custom.skip.* {*;}
-keep class com.dsp.librarycore.litepal** {*;}
-keep class com.dsp.librarycore.entity.AdRewardTradeBean {*;}
-keep class com.dsp.librarycore.AdSdkConfig {*;}
-keep class com.dsp.librarycore.config.AdConfig {*;}
-keep class com.dsp.librarycore.utils.UtilsInit {*;}
-keep class com.dsp.librarycore.utils.UtilsInit {*;}
-keep class com.dsp.librarycore.db.LitePalHelper {*;}
-keep class com.dsp.librarycore.xutils** {*;}
-keep class com.dsp.librarycore.utils.SharedHelper {*;}
-keep class com.dsp.librarycore.net.NetResultHandlerInit {*;}
-keep class com.dsp.librarycore.handler.MyUncaughtExceptionHandler {*;}
-keep class com.dsp.librarycore.handler.XQueueManager {*;}
-keep class com.dsp.libraryjuhe.wybd.provider.BdProvider {*;}
-keep class com.dsp.libraryjuhe.wycsj.provider.CsjProvider {*;}
-keep class com.dsp.libraryjuhe.wygdt.provider.GdtProvider {*;}

# 保护代码中的Annotation不被混淆，这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*
#避免混淆泛型，这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature
#抛出异常时保留代码行号，在异常分析中可以方便定位
-keepattributes SourceFile,LineNumberTable
#保持native方法不被混淆
#keepclasseswithmembernames 保留类和该类中所有带native限定符的方法
-keepclasseswithmembernames class * {
    native <methods>;
}
# 对于R（资源）下的所有类及其方法，都不能被混淆
-keep class **.R$* {
    *;
}
# 对于带有回调函数onXXEvent的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.baidu.mobads** { *; }
-keep class com.baidu.mobad** { *; }
-keep class com.bun.miitmdid.core** {*;}

-keep class com.bytedance.sdk.openadsdk** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew** {*;}
-keep class com.pgl.sys.ces** {*;}
-keep class com.bytedance.embed_dr** {*;}
-keep class com.bytedance.embedapplog** {*;}

# Demo工程里用到了AQuery库，因此需要添加下面的配置
# 请开发者根据自己实际情况给第三方库的添加相应的混淆设置
-dontwarn com.androidquery**
-keep class com.androidquery** { *;}
-dontwarn tv.danmaku**
-keep class tv.danmaku** { *;}
-dontwarn androidx**
# 如果使用了tbs版本的sdk需要进行以下配置
-keep class com.tencent.smtt** { *; }
-dontwarn dalvik**
-dontwarn com.tencent.smtt**
```

### 支持架构

**注意:** SDK中使用的so文件支持五种架构：**x86,x86_64,armeabi,armeabi-v7a,arm64-v8a**如果您应用中支持的架构超出这五种，请在`build.gradle`中使用`abiFilters`选择支持的架构。如下所示：

```groovy
ndk { // 设置支持的 SO 库构架，注意这里要根据你的实际情况来设置
   abiFilters 'x86','x86_64','armeabi','armeabi-v7a','arm64-v8a'
}
```



## SDK 初始化配置

### 初始化配置

开发者需要在`Application#onCreate()`方法中调用以下代码来初始化sdk。

```java
public class MyApplication extends ActLifecycleAppBase {


    @Override
    public void onCreate() {
        super.onCreate();
        
        AdSdkConfig.init(this, "wyl_1321321");
        AdSdkConfig.allAdListener = new WjAllAdListener() {
            @Override
            public void onAdStartRequest(String providerType, AdItemEntity itemEntity) {
            }

            @Override
            public void onAdFailed(String providerType, AdItemEntity itemEntity, String failedMsg) {
            }

            @Override
            public void onAdLoaded(String providerType, AdItemEntity itemEntity) {
            }
        };
    }
}
```

### 初始化其他配置参数说明

**主要Api**

* **AdSdkConfig**中接口：

| 方法名 | 方法介绍 |
| ----- | ------ |
| delayRequestMaxFetch | 非必要设置 - 广告最大拉取延时时间ms（ 请求广告的超时时间；3000 ≤ value ≥ 10000 ） |
| sdkPrintLogEnable | 是否打印 Log 日志 |
| allAdListener | 所有广告商所有广告类型的广告都会回调这个监听器, 主要是方便做统计：请求成功率、请求失败信息等 |



## 开屏广告

** 简介

**开屏广告：** 开屏广告为用户在进入App时展示的全屏广告。开屏广告为一个View，宽高默认为match_parent。
**注意：** 开屏广告view: width =屏幕宽；height需要>=75%屏幕高 ，否则会影响计费。

**参考**: 具体示例详见Demo中的`SplashActivity`，`SplashMenuLoadAfterShowActivity`，`SplashMenuLoadAndShowActivity`

**主要Api:**

- **AdHelperSplashShow**中方法：
- **AdHelperSplashPart**中方法：

| 方法名                                                       | 方法介绍                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| new AdHelperSplashShow(Activity activity, String mAdPlaceId, WjSplashListener listener) | 构造方法, mAdPlaceId广告位的ID, WjSplashListener接口监听     |
| show(ViewGroup container)                                    | 在container中显示广告内容, 高度height需要>=75%屏幕高 ，否则会影响计费 |
| destroy()                                                    | 在Activity生命周期onDestroy()方法中调用                      |


| 方法名                                                       | 方法介绍                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| new AdHelperSplashPart(Activity activity, String mAdPlaceId, WjSplashListener listener) | 构造方法, mAdPlaceId广告位的ID, WjSplashListener接口监听     |
| loadOnly(ViewGroup container)                                    | 在container中显示广告内容, 高度height需要>=75%屏幕高 ，否则会影响计费 |
| loadOnly(ViewGroup container, BaseSkipView skipView, int delaySplashTime) | 在container中显示广告内容, 高度height需要>=75%屏幕高 ，否则会影响计费, 自定义跳过控件, 自定义开屏时间(毫秒) |
| show()                                    | 展示 |
| destroy()                                                    | 在Activity生命周期onDestroy()方法中调用                      |


* **WjSplashListener**回调接口

| 方法名 | 方法介绍 |
| ------ | -------- |
| onAdStartRequest | 开始请求广告内容 |
| onAdLoaded | 广告请求到了 |
| onAdVideoCached | 广告如果为视频类型已经缓存了 |
| onAdFailed | 广告请求失败 |
| onAdFailedAll | 广告请求全部失败 |
| onAdClicked | 广告点击了 |
| onAdExpose | 广告曝光了 |
| onAdClose | 广告关闭了 |

**注意点:**

1. 开屏请求展示一定要注意展示区域的大小设置, 切记不可使用错误

2. 配合初始化类`AdSdkConfig`中`delayRequestMaxFetch`的变量, 配置开屏广告加载时间, 可以不用自定义, 如果需要自定义超时时间**建议大于3500ms**，最大程度的保证广告的展示率可开屏体验

3. 配合初始化类`AdSdkConfig`中的`delaySplashFetch`的变量, 设置在没有广告时停留的时间, 在没有获取到广告时不会一闪而过

**平台接入:**

* 广告位管理

  mAdPlaceId的获取

![image-20210504164749129](C:\Users\ghhg\AppData\Roaming\Typora\typora-user-images\image-20210504164749129.png)



## 插屏广告

**简介

**模版渲染插屏**：开发者不用自行对广告样式进行编辑和渲染，可直接调用相关接口进行广告展示。

> 支持的多种广告尺寸：需要在平台控制设置

**参考**: 具体示例详见Demo中的`InterActivity`

**主要Api:**

- **AdHelperInter**中方法：

| 方法名                                                       | 方法介绍                                                |
| ------------------------------------------------------------ | ------------------------------------------------------- |
| new AdHelperInter(Activity activity, String mAdPlaceId, WjInterListener listener) | 构造方法, mAdPlaceId广告位的ID, WjInterListener接口监听 |
| load()                                                       | 获取广告                                                |
| show()                                                       | 展示广告                                                |
| destroy()                                                    | 在Activity生命周期onDestroy()方法中调用                 |

- **WjInterListener**回调接口：
*
| 方法名           | 方法介绍                     |
| ---------------- | ---------------------------- |
| onAdStartRequest | 开始请求广告内容             |
| onAdLoaded       | 广告请求到了                 |
| onAdVideoCached  | 广告如果为视频类型已经缓存了 |
| onAdFailed       | 广告请求失败                 |
| onAdFailedAll    | 广告请求全部失败             |
| onAdClicked      | 广告点击了                   |
| onAdExpose       | 广告曝光了                   |
| onAdClose        | 广告关闭了                   |

**注意点:**

1. 期望个性化模板尺寸的参数设置中，尺寸大小请保持和平台设置一致

2. 广告特殊比例尺寸的设置跟平台的联系



## Banner横幅广告

** 简介

**模版渲染Banner**：开发者不用自行对广告样式进行编辑和渲染，可直接调用相关接口进行广告展示。

> 不支持开发者在view添加按钮及对广告拦截处理

**参考**: 具体示例详见Demo中的`BannerActivity`

**主要Api:**

- **AdHelperBanner**中方法：

| 方法名                                                       | 方法介绍                                                 |
| ------------------------------------------------------------ | -------------------------------------------------------- |
| AdHelperBanner(Activity activity, String mAdPlaceId, WjBannerListener listener) | 构造方法, mAdPlaceId广告位的ID, WjBannerListener接口监听 |
| show(ViewGroup container)   | 在container中显示广告内容    |
| destroy()    | 在Activity生命周期onDestroy()方法中调用      |

- **WjBannerListener **回调接口：
*
| 方法名           | 方法介绍                     |
| ---------------- | ---------------------------- |
| onAdStartRequest | 开始请求广告内容             |
| onAdLoaded       | 广告请求到了                 |
| onAdFailed       | 广告请求失败                 |
| onAdFailedAll    | 广告请求全部失败             |
| onAdClicked      | 广告点击了                   |
| onAdExpose       | 广告曝光了                   |
| onAdClose        | 广告关闭了                   |

**注意点:**

1. banner广告有其特有的使用场景 横幅广告是在内容底部或顶部显示的小条形广告 暂不支持在列表中使用
2. banner广告的显示view, 宽度适应手机屏幕
3. 平台上已经创建好的代码位ID，不支持修改尺寸，因此要求开发者按照实际需求进行创建。



## 信息流 - 原生模板渲染

**简介

**模版渲染信息流**：支持图文和视频样式，开发者不用自行对广告样式进行编辑和渲染，可直接调用相关接口获取广告view去进行展示,开发者可以随时模板进行样式上的调整。降低了接入成本的前提下，不会影响cpm的水平，仍然可以保持高的竞争力。

> 支持的广告尺寸：开发者在穿山甲平台上可以进行多模板、多尺寸的勾选

**参考**: 具体示例详见Demo中的`NativeExpressSingleActivity`或者`NativeExpressRecyclerViewActivity`

**主要Api:**

- **AdHelperNativeTemplateExpress**中方法：

| 方法名     | 方法介绍              |
| ------------------------------------------------------------ | -------------------------------------------------------- |
| AdHelperNativeTemplateExpress(Activity activity, String mAdPlaceId, WjNativeListenerlistener,  int adCount) | 构造方法, mAdPlaceId广告位的ID, WjNativeListener接口监听, adCount请求广告实体的个数   |
| load()   | 加载广告内容    |
| destroy()     | 在Activity生命周期onDestroy()方法中调用   |

- **WjNativeListenerlistener **回调接口：
*
| 方法名           | 方法介绍         |
| ---------------- | ---------------- |
| onAdStartRequest | 开始请求广告内容 |
| onAdLoaded       | 广告请求到了     |
| onAdFailed       | 广告请求失败     |
| onAdFailedAll    | 广告请求全部失败 |

- **NativeTemplateExpressView**中方法：
- 获取到广告实体, 通过这类`NativeTemplateExpressView`展示

- | 方法 | 方法介绍 |
- | showTemplate(Activity activity, Object adObject, ViewGroup container, WjNativeViewListener listener) | adObject广告实体, 广告展示布局,  广告展示回调的WjNativeViewListener接口 |

- **WjNativeViewListener **回调接口：

| 方法名           | 方法介绍         |
| ---------------- | ---------------- |
| onAdShow | 开始展示 |
| onAdClicked       | 广告点击     |
| onAdRenderSuccess       | 广告渲染成功    |
| onAdRenderFailed    | 广告渲染失败 |
| onAdClosed    | 广告关闭 |
| onAdStatusChanged    | 广告状态改变 |



## 小视频

**简介

**模版渲染小视频**：类似抖音样式的展示广告

**参考**: 具体示例详见Demo中的`NativeDrawVideoListActivity`

**主要Api:**

- **AdHelperNativeDrawMovie**中方法：

| 方法名                                                       | 方法介绍                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| AdHelperNativeDrawMovie(Activity activity, String mAdPlaceId, WjNativeListener listener) | 构造方法, mAdPlaceId广告位的ID, WjNativeListener 接口监听, adCount请求的广告数 |
| load(int adCount)     | 加载adCount条广告, 1~3中间  建议设置为1    |
| show(Object adObject, ViewGroup container)     | adObject是在WjNativeListener回调接口中获取的, 确保非空,  container为广告填充的布局     |
| destroy()           |   销毁数据                         |

- **WjNativeListener **回调接口：

| 方法名           | 方法介绍         |
| ---------------- | ---------------- |
| onAdStartRequest | 开始请求广告内容 |
| onAdLoaded       | 广告请求到了     |
| onAdFailed       | 广告请求失败     |
| onAdFailedAll       | 广告请求失败     |



## 激励视频广告

** 简介

激励视频广告需要让用户主动选择去观看，广告的效果为观看完毕视频广告，发放奖励给用户。

**使用场景包括但不限于**：

1. 游戏等应用内观看视频广告获得游戏内金币等；
2. 积分类应用接入；

**参考**: 具体示例详见Demo中的`RewardActivity`

**主要Api:**

- **AdHelperReward**中方法：

| 方法                                                         | 方法介绍                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| AdHelperReward(Activity activity, WjRewardListener listener) | 构造方法, mAdPlaceId广告位的ID, AdRewardTradeBean用于激励验证必须传值, WjRewardListener接口监听, adCount请求的广告数 |
| load(String mAdPlaceId, AdRewardTradeBean tradeBean) |广告获取; mAdPlaceId为广告位id, tradeBean激励的奖励|
| show | 广告展示|
| destroy | 广告销毁,在Activity生命周期onDestroy()方法中调用|


- **WjRewardListener **回调接口：

|void onAdLoaded();                      | 请求到了广告 |
|void onAdClicked();                     | 广告被点击了 |
|void onAdShow();                        | 广告展示了 |
|void onAdExpose();                      | 广告曝光了 （ 和 onAdShow 的区别是展示不一定曝光，曝光一定展示，需要展示一定的时间才会曝光，曝光的条件是提供商规定的 ） |
|void onAdVideoComplete();               | 视频广告播放完成 |
|void onAdVideoCached();                 | 视频缓存完成 |
|void onAdRewardVerify();                | 奖励被验证 |
|void onAdClose();                       | 广告被关闭了 |
|void getTrade(AdRewardTradeBean trade); | 验证的id |

**注意点:**
1. 有横竖屏的区分, 要用到不同的mAdPlaceId对应不同的场景, 横屏mAdPlaceId和竖屏mAdPlaceId分别在用户后台申请;




## 视频贴片

**简介

贴片广告是常用于视频组件，用于给视频贴片，在视频预加载，暂停或结束时使用。
**适用场景:**视频组件的自然停顿点，适合投放这类广告

**参考**: 具体示例详见Demo中的`NativePatchVideoActivity`

**主要Api:**

- **AdHelperNativePatchExpress**中方法：

| 方法                                                         | 方法介绍                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| new AdHelperNativePatchExpress(Activity activity, String mAdPlaceId, WjPatchVideoListener listener) | 构造方法, mAdPlaceId广告位的ID, WjPatchVideoListener接口监听 |
| show(RelativeLayout container, int time)        | 广告展示, time贴边是图片的时候显示的倒计时    |
| onConfigurationChangedPatchVideo(int ORIENTATION)      | 横竖屏的设置             |
| destroy()      | 广告销毁,在Activity生命周期onDestroy()方法中调用             |

- **WjPatchVideoListener **回调接口：

|void onAdLoaded();                      | 请求到了广告 |
|void onAdClicked();                     | 广告被点击了 |
|void onAdShow();                        | 广告展示了 |
|void onAdVideoComplete();                      | 广告曝光了 （ 和 onAdShow 的区别是展示不一定曝光，曝光一定展示，需要展示一定的时间才会曝光，曝光的条件是提供商规定的 ） |
|void onAdClose();                       | 广告被关闭了 |
|boolean onAdCloseJudge();               | 点击关闭按钮的时候的拦截, false为倒计时未完成前不让关闭(默认), true随时点击可关闭 |



## 错误码

| 错误码 | 说明|
| ------------ | ---------------- |
|0	     |无广告返回|
|103010	 |应用ID信息缺失|
|103011	 |应用ID信息错误，MSSP未收录|
|103012	 |应用ID信息无效，MSSP上未生效|
|103060	 |应用包名信息错误，请保证注册包名和实际请求包名一致|
|107001	 |广告位ID未收录|
|107002	 |广告位ID未启用|
|107003	 |广告位ID与APPSID不匹配|
|1020001 |	网络连接失败|
|1040001 |	请求时使用了错误的参数，比如使用错误的广告位ID|
|1040003 |	请求超时|
|3030002 |	缓存物料失败|
|3040001 |	广告展现标准不达标|
|403	 |应用id匹配错误，请检查appsid是否正确|
|400	 |请求时使用了错误的参数|
|5XX	 |服务器系统错误|
|1001	 |请求时使用了错误的参数|
|10004	 |服务器忙|
|20001	 |广告请求失败|
|40004	 |广告位不能为空|
|40005	 |广告位尺寸不能为空|
|40006	 |广告位ID不合法|
|40007	 |广告数量错误|
|40008	 |图片尺寸错误|
|40009	 |媒体ID不合法|
|40013	 |非开屏广告请求方法使用了开屏代码位ID|
|40016	 |代码位ID与应用ID不匹配或者应用ID缺失|
|40018	 |平台上录入的包名与项目里的包名不一致|
|40019	 |广告请求方法与代码位类型不匹配|
|40020	 |开放注册新上线广告位超出日请求量限制|
|40021	 |apk签名SHA1值与媒体平台录入的SHA1不一致|
|40022	 |广告请求方法与代码位渲染方式不一致|
|40024	 |SDK版本过低不返回广告|
|40025	 |渲染异常|
|40026	 |海外ip请求中国服务器导致|
|40029	 |模板渲染类型广告的请求方法或请求参数不正确|
|40042	 |新插屏广告使用SDK版本过低|
|50001	 |服务器错误|
|60007	 |激励视频验证服务器异常或处理失败|
|-1	    |数据解析失败|
|-2		|网络错误|
|-3		|解析数据没有ad|
|-4		|返回数据缺少必要字段|
|-5		|BannerAd加载图片失败|
|-6		|插屏广告图片加载失败|
|-7		|开屏广告图片加载失败|
|-8		|频繁请求|
|-10	|缓存解析失败|
|-11	|缓存过期|
|-12	|缓存中没有开屏广告|
|101	|渲染结果数据解析失败|
|102	|未匹配到主模板|
|103	|未匹配到子模板|
|107	|模板渲染超时未回调|
|108	|模板广告加载超时无返回|
|109	|模板加载失败|
|601	|链接建立失败|
|602	|链接建立超时|
|603	|广告返回异常|
|604	|广告解析失败|
|605	|广告解析失败|
|606	|广告解析异常|
|607	|广告读写失败|
|608	|广告读写失败|
|609	|广告下载失败|
|610	|广告下载异常|
|611	|广告请求被取消|
|612	|图片加载异常|
|100001	|请求解析失败，常见原因是没有对参数进行url编码。|
|100007	|广告位id参数解析失败，该参数必填并且参数类型是非负整数|
|100012	|广告位宽度参数解析失败，该参数类型是非负整数|
|100014	|广告位高度参数解析失败，该参数类型是非负整数|
|100016	|广告位参数page_number字段数据类型错误，该参数类型为非负整数|
|100017	|广告位参数last_ad_ids字段数据类型错误，该参数类型为字符串|
|100019	|广告位参数is_information_pos字段数据类型错误，该参数类型为布尔类型|
|100023	|广告位参数level字段数据类型错误，该参数类型为非负整数|
|100028	|广告位参数query字段数据类型错误，该参数类型为字符串|
|100031	|广告位参数max_duration字段数据类型错误，应为非负整数|
|100032	|广告位参数traffic_type字段数据类型错误，应为非负整数|
|100034	|广告位参数support_c2s字段数据类型错误，应为非负整数|
|100125	|广告位宽度和高度参数无效，请参考广告位宽度和高度参数说明|
|100133	|请求中包含无效的广告位|
|100135	|广告位状态冻结|
|100159	|激励视频请求orientation参数不合法|
|100303	|ad_count参数必填且应为非负整数|
|100351	|need_rendered_ad参数无效|
|102006	|没有匹配到合适的广告。禁止重试，否则可能触发系统策略导致流量收益下降|
|104014	|原始idfa无效|
|104015	|imei无效|
|104017	|android id无效|
|104018	|android_advertising_id无效|
|106001	|广告位不存在|
|107000	|广告位信息为空|
|107005	|api请求中app_id不匹配|
|107006	|api请求中安卓的package name或是ios的bundle id不合法|
|107007	|缺少有效的设备标识字段|
|107008	|广告位所属媒体在联盟平台关联的域名为空|
|107009	|广告位所在页面的域名与广告位所属媒体在联盟平台关联的域名不一致|
|107011	|请求中的操作系统类型与广告位在联盟平台的设置不匹配|
|107012	|解析api请求中device字段失败|
|107014	|解析api请求中network字段失败|
|107015	|解析api请求中geo字段失败|
|107016	|解析api请求中设备品牌和型号model字段失败|
|107017	|解析api请求中设备横竖屏orientation字段失败|
|107018	|解析api请求中网络连接类型connect_type字段失败|
|107019	|解析api请求中运营商信息carrier字段失败|
|107020	|解析api请求中纬度信息lat字段失败|
|107021	|解析api请求中经度信息lng字段失败|
|107022	|解析api请求中经纬度精度location_accuracy字段失败|
|107023	|解析api请求中是否支持大规格插屏广告support_full_screen_interstitial字段失败|
|107024	|解析api请求中操作系统os_version字段失败|
|107025	|解析api请求中屏幕宽度screen_width字段失败|
|107026	|解析api请求中屏幕高度screen_height字段失败|
|107027	|解析api请求中pos字段失败|
|107028	|解析api请求中media字段失败|
|107029	|api请求中缺少合法的广告位宽度或高度|
|107030	|请求中app包名与广告位在联盟平台的设置不匹配|
|107031	|解析api请求中设备制造商manufacturer字段失败|
|107032	|解析api请求中设备类型device_type字段失败|
|107033	|api请求中缺少合法的设备id|
|107034	|错误的sdk接口调用，常见原因比如使用原生广告位id但调用开屏广告位接口|
|107035	|不支持模板视频广告的sdk版本，请升级sdk版本或广告位配置为不展示视频广告|
|107036	|禁止广告展示页面嵌在iframe中|
|107040	|错误的sdk接口调用，常见原因比如使用自渲染2.0广告位id但调用自渲染1.0接口|
|107041	|sdk版本已经废弃，需更新|
|107042	|api请求中adx_id不合法|
|107044	|API请求中oaid无效|
|107045	|API请求中aid_ticket无效|
|107046	|API请求中taid_ticket无效|
|107047	|sdk的banner插屏1.0已废弃，请通过2.0接入|
|107048	|sdk的原生自渲染1.0已废弃，请通过2.0接入|
|107049	|Js旧准入规则已废弃，请升级准入规则|
|107050	|sdk接口与广告位不匹配。广告位是模板2.0广告位，请调用sdk模板2.0接口请求广告|
|107051	|解析api请求中设备启动时间device_start_sec字段失败|
|107052	|解析api请求中国家country字段失败|
|107053	|解析api请求中语言language字段失败|
|107054	|解析api请求中设备名称的MD5值device_name_md5字段失败|
|107055	|解析api请求中设备machine值hardware_machine字段失败|
|107056	|解析api请求中设备model值hardware_model字段失败|
|107057	|解析api请求中物理内存physical_memory_byte字段失败|
|107058	|解析api请求中硬盘大小harddisk_size_byte字段失败|
|107059	|解析api请求中系统更新时间system_update_sec字段失败|
|107060	|解析api请求中时区time_zone字段失败|
|109506	|该广告位样式处于测试期且今日的请求量已经达到了上限，请明日00:30后再发送请求|
|109507	|该广告位样式处于测试期且每小时请求量已经达到了上限，请一小时后再发送请求|
|109511	|该广告位数据异常已被暂时封禁，请明日00:30后再发送请求|
|112001	|请求合法，但当前暂无资讯内容返回,禁止重试，请稍后重试|
|112003	|channel无效|
|112004	|广告位类型错误，非资质通荐广告位请求了资质通荐广告接口，请修改广告位ID后进行重试。|




## 常见问题

### *通用问题 ###


**为什么返回的广告中有重复广告？**
同一个广告主在广告后台会使用相似的素材内容创建多个广告计划，因此平台在返回的广告中可能会有很多相似内容的广告，但是这些广告对应的广告计划ID是不相同的。


**广告去重规则是什么？**
1. 展示或者点击因为重复被去重：一次请求返回的广告，如果用户多次看到或者点击，有效展示和点击只记录一次，客户端展示点击会回调多次，广告在服务端进行去重；
2. 超时去重：广告从下发到展示的有效时限为1小时，如果超过一个小时展示，不会被计算为一个有效展示，所以提醒开发者要注意广告的缓存时间，超过一个小时，请重新请求新的广告来展示。


**激励视频和全屏视频可以强制关闭吗？**
全屏视频支持5s跳过，激励视频必须看完。


**资源混淆**
如果您的应用对资源也进行混淆（如andResGuard），请不要混淆本引力广告的任何资源，防止资源找不到崩溃.



### *开发接入问题 ###


**如果包名重复，且能证明包名被占用，请在联系我们的客服人员。**


**如何获取sha1**
不同签名文件的SHA1值不同，可以参考下面获取SHA1值的方式：
通过Android Studio编译器获取
打开Android Studio的Terminal工具
输入命令：keytool -v -list -keystore keystore文件路径
输入Keystore密码


**如何上传签名apk**
关键是将下载的未签名的包, 使用开发的apk的签名文件签名
jarsigner -verbose -keystore [签名文件路径] -signedjar [签名后apk的文件路径] [未签名apk的文件路径] [证书别名]
```
jarsigner -verbose -keystore debug.keystore -signedjar ./mssp-verify-signed.apk ./mssp-verify.apk android.keystore
```


### *关于应用校验及常见问题的说明 ###


**什么是APK下载链接、详情页链接？**
APK链接：点击后可以直接唤起APK应用包下载的链接，即为APK链接。例如：https://imtt.dd.qq.com/16891/DBB2D18C1A390C6AC9DB067C94F57F3E.apk?fsname=com.yunbu.magicgarden.tencent_1.1.13_10113.apk&csr=1bbd
详情页链接：即应用商店的下载详情页，例如：http://info.appstore.vivo.com.cn/detail/2853962

**安卓应用校验方式**
自动校验：当您填写所选应用商店的apk下载链接时，则自动进行校验。您所填写的链接必须包含该应用商店域名。获取方式参考以下几种：

方法一：您可以在应用商店将此应用下载，并在“下载内容”中复制该链接地址，适合使用此方法的商店包括：应用宝、小米/小米游戏、vivo游戏/vivo、应用汇、9游、安智、乐商店、2345、爱奇艺游戏、TikTok。

方法二：OPPO商店获取apk下载链接步骤:

第一步：https://app.cdo.oppomobile.com/home/detail?app_id=（此处拼接OPPO分配的审核成功的Appid）；

第二步：通过浏览器打开第一步拼接好的详情页链接进行下载，并在“下载内容”中复制该链接地址。

>注：个别应用可能无法通过此方法获取下载地址，若出现无法获取的情况建议使用其他商店下载地> 址进行应用创建

方法三：查看应用商店的开发者管理后台，如提供APK下载链接地址，复制并填写即可。目前确认可提供此链接的商店包括：4399、豌豆荚、搜狗、美图；

方法四：如果您选择的应用商店，会跳转到UC、PP助手等其他商店，请按照域名选择相应渠道。
我们会对一个应用进行双重校验。
第一，我们校验应用的真实性，您需要填写应用的包名、链接；
第二，我们校验应用的唯一性，您需要填写完整的正确包名；
第三，我们校验应用所有权的真实性，您需要填写SHA1值。
创建失败的常见原因
应用下载出错：除了苹果商店、好游快爆、TapTap、华为商店、360和魅族，您都需要填写APK下载链接，如果您填写了详情页链接，则无法创建成功；另外，如果此APK链接无法顺利下载，也会出现“下载出错”的提示

解析超时：由于网络问题或包体太大（大于250M），会出现下载超时，这种情况下，该应用将被转为人工审核
SHA1、包名填写错误：请检查所填写内容是否是该应用真实正确的SHA1、包名
包名重复：说明此应用在穿山甲已有应用ID，如果您确实为实际开发者，请提交相关证明（软著等）到咨询窗口，我们将停止冒用者的应用。除此之外，我们不接受在未经授权的情况下使用其他开发者的应用在本平台获利，请知悉
包名解析出错： 本平台无法根据您提供的商店链接找到应用，常见于itunes链接。主要原因为您的APP只在特定国家/地区上架，需要在链接中加入国家/地区代码，才能获取应用。
例:抖音中国版
（https://apps.apple.com/cn/app/id1142110895） 
如果去掉链接中的cn
（https://apps.apple.com/app/id1142110895）则无法从商店获取相关信息。







