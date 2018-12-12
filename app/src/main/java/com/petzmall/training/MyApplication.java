package com.petzmall.training;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alivc.player.AliVcMediaPlayer;
import com.aspsine.multithreaddownload.DownloadConfiguration;
import com.aspsine.multithreaddownload.DownloadManager;
import com.baidu.mapapi.SDKInitializer;
//import com.github.retrofitutil.NetWorkManager;
import com.bumptech.glide.request.target.ViewTarget;
import com.petzmall.training.view.CustomViewWithTypefaceSupport;
import com.petzmall.training.view.TextField;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.lang.reflect.Field;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyApplication extends MultiDexApplication {

    private  static MyApplication myApplication;
    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mContext = getApplicationContext();
        ViewTarget.setTagId(R.id.glide_tag);//gilde bug
        Typeface mTypeface = Typeface.createFromAsset(getAssets(), "fonts/SourceHanSansCN-Normal.ttf");
        try {
            Field field = Typeface.class.getDeclaredField("MONOSPACE");
            field.setAccessible(true);
            field.set(null, mTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //配置数据库
//        setupDatabase();
//        if(true&&BuildConfig.DEBUG){                           //http://192.168.0.19:20001/        //http://121.40.186.118:5108
//         //   NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5108",BuildConfig.DEBUG).complete();

             NetWorkManager.getInstance(getApplicationContext(),"http://www.wanandroid.com/tools/mockapi/12410/",true).complete();
//        }else{
//          //  NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5008",BuildConfig.DEBUG).complete();
//            NetWorkManager.getInstance(getApplicationContext(),"http://1v8z769925.51mypc.cn:8081/outer/",false).complete();
//        }
       // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//        SDKInitializer.initialize(this);
//        if(true&&BuildConfig.DEBUG){                           //http://192.168.0.19:20001/        //http://121.40.186.118:5108
//            NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5108",BuildConfig.DEBUG).complete();
//
//           // NetWorkManager.getInstance(getApplicationContext(),"http://192.168.0.19:20001",BuildConfig.DEBUG).complete();
//        }else{
//           NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5008",BuildConfig.DEBUG).complete();
////            NetWorkManager.getInstance(getApplicationContext(),"http://192.168.0.19:20001",BuildConfig.DEBUG).complete();
//        }

        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this,"5bc456cbb465f56fd9000083"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");

        PlatformConfig.setWeixin("wx4edcae15f4e4f4c4", "654cd3c01f839310c0480cc8086afa05");
        initDownloader();

        //初始化播放器
        AliVcMediaPlayer.init(getApplicationContext());
    }

    /**
     * 获取Application
     */
    public static MyApplication getApp() {
        return myApplication;
    }



    public static Context getContext(){
        return mContext;
    }
    @Override
    public void onTerminate() {

        super.onTerminate();
        //整体摧毁的时候调用这个方法
    }

    private void initDownloader() {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(10);
        configuration.setThreadNum(3);
        DownloadManager.getInstance().init(getApplicationContext(), configuration);
    }


}
