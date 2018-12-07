package com.petzmall.training;

import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.github.androidtools.SPUtils;
import com.next.easynavigition.view.EasyNavigitionBar;
import com.petzmall.training.base.BaseActivity;
import com.petzmall.training.broadcast.MyOperationBro;
import com.petzmall.training.module.category.fragment.CategoryFragment;
import com.petzmall.training.module.home.fragment.HomeFragment;
import com.petzmall.training.module.my.fragment.MyFragment;
import com.petzmall.training.module.socialCircle.fragment.SocialCircleFragment;
import com.petzmall.training.tools.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity  {

    HomeFragment homeFragment;
    CategoryFragment categoryFragment;
    SocialCircleFragment socialCircleFragment;
    MyFragment myFragment;


    @BindView(R.id.navigitionBar)
    EasyNavigitionBar navigitionBar;

    private LocalBroadcastManager localBroadcastManager;
    private MyOperationBro myOperationBro;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] tabText = {"首页", "分类", "圈子", "我的"};
    //未选中icon
    private int[] normalIcon = {R.drawable.home_normal, R.drawable.category_normal, R.drawable.circle_normal, R.drawable.my_normal};
    //选中时icon
    private int[] selectIcon = {R.drawable.home_select, R.drawable.category_select, R.drawable.circle_select, R.drawable.my_select};
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();

//        getRxBusEvent(SelectSheQuEvent.class, new MySubscriber() {
//            @Override
//            public void onMyNext(Object o) {
//                selectSheQu();
//                selectButton.setChecked(true);
//            }
//        });
    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        if (intent == null) {
//            return;
//        } else if (Config.backHome.equals(intent.getAction())) {
//            selectHome();
//            selectButton.setChecked(true);
//        } else if (Config.useVoucher.equals(intent.getAction())) {
//            selectCategory();
//            selectButton.setChecked(true);
//        }
//    }

    @Override
    protected void initView() {
//        StatusBarUtil.setColor(MainActivity.this,getResources().getColor(R.color.red));

//        String registrationID = JPushInterface.getRegistrationID(mContext);
//        android.util.Log.i("registrationID","registrationID====="+registrationID);
//        if(!TextUtils.isEmpty(registrationID)){
//            SPUtils.setPrefString(mContext,Config.jiguangRegistrationId,registrationID);
//        }
        fragments.add(new HomeFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new SocialCircleFragment());
        fragments.add(new MyFragment());

        boolean isUpdatePwd = SPUtils.getPrefBoolean(mContext, Config.isUpdatePWD, false);
        if (isUpdatePwd) {
            SPUtils.removeKey(mContext, Config.isUpdatePWD);
            SPUtils.removeKey(mContext, Config.user_id);
        }
        setBroadcast();
//        int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.height = statusBarHeight;
//        status_bar.setLayoutParams(layoutParams);
//        status_bar.setBackgroundColor(getResources().getColor(R.color.green));

//        homeFragment = new HomeFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homeFragment).commitAllowingStateLoss();


        navigitionBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .iconSize(25)
                .navigitionHeight(48)  //导航栏高度
                .lineHeight(1)         //分割线高度  默认1px
                .lineColor(Color.parseColor("#f2f2f2"))
                .normalTextColor(Color.parseColor("#CECEDA"))   //Tab未选中时字体颜色
                .selectTextColor(Color.parseColor("#5A92F0"))   //Tab选中时字体颜色
                .fragmentManager(getSupportFragmentManager())
                .canScroll(false)
                .build();
    }

    private void setBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        myOperationBro = new MyOperationBro(new MyOperationBro.LoginBroInter() {
            @Override
            public void loginSuccess() {
//                selectMy();

//                registerHuanXin();
            }

            @Override
            public void exitLogin() {
//                selectHome();
                myFragment = null;
            }
        });
        localBroadcastManager.registerReceiver(myOperationBro, new IntentFilter(Config.Bro.operation));
    }

    //    private void registerHuanXin() {
//        RXStart(new IOCallBack<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                if(TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.phone,null))){
//                    return;
//                }
//                ChatClient.getInstance().createAccount(SPUtils.getPrefString(mContext,Config.phone,null), "123456", new Callback(){
//                    @Override
//                    public void onSuccess() {
//                    }
//                    @Override
//                    public void onError(int i, String s) {
//                        Log.i("===",i+"=onError=="+s);
//                    }
//                    @Override
//                    public void onProgress(int i, String s) {
//                        Log.i("===",i+"=onProgress=="+s);
//                    }
//                });
//            }
//
//            @Override
//            public void onMyNext(String s) {
//
//            }
//        });
//    }
    public EasyNavigitionBar getNavigitionBar() {
        return navigitionBar;
    }


    @Override
    protected void initData() {
//        updateApp();
//        //获取微信，支付宝支付的通知地址,支付方式1支付宝，2微信
//        getZhiFuNotifyUrl("1");
//        getZhiFuNotifyUrl("2");
//        getAdverImg();
    }

    @Override
    protected void onViewClick(View v) {

    }
//    private void updateApp() {
//        Map<String,String>map=new HashMap<String,String>();
//        map.put("rnd",getRnd());
//        map.put("sign",GetSign.getSign(map));
//        ApiRequest.getAppVersion(map, new MyCallBack<AppVersionObj>(mContext) {
//            @Override
//            public void onSuccess(AppVersionObj obj) {
//                if(obj.getAndroid_version()>getAppVersionCode()){
//                    SPUtils.setPrefString(mContext,Config.appDownloadUrl,obj.getAndroid_vs_url());
//                    SPUtils.setPrefBoolean(mContext,Config.appHasNewVersion,true);
//                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
//                    mDialog.setMessage("检测到app有新版本是否更新?");
//                    mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            AppInfo info = new AppInfo();
//                            info.setUrl(obj.getAndroid_vs_url());
//                            info.setHouZhui(".apk");
//                            info.setFileName("yangyu");
//                            info.setId(obj.getAndroid_version() + "");
//                            downloadApp(info);
//                        }
//                    });
//                    mDialog.create().show();
//                }else{
//                    SPUtils.setPrefBoolean(mContext,Config.appHasNewVersion,false);
//                }
//            }
//        });
//    }

    //    private void downloadApp(AppInfo info) {
//        MyAPPDownloadService.intentDownload(mContext, info);
//    }
//    public int getAppVersionCode() {
//        Context context=mContext;
//        int versioncode = 1;
//        try {
//            PackageManager pm = context.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
//            String versionName = pi.versionName;
//            versioncode = pi.versionCode;
//            return versioncode;
//        } catch (Exception e) {
//            android.util.Log.e("VersionInfo", "Exception", e);
//        }
//        return versioncode;
//    }
//    private void getAdverImg() {
//        Map<String,String>map=new HashMap<String,String>();
//        map.put("rnd",getRnd());
//        map.put("sign",GetSign.getSign(map));
//        ApiRequest.getAdverImg(map, new MyCallBack<AdverImgObj>(mContext) {
//            @Override
//            public void onSuccess(AdverImgObj obj) {
//                if(TextUtils.isEmpty(obj.getImage_url())){
//                    SPUtils.removeKey(mContext,Config.imgPath);
//                }else{
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            saveImg(obj.getImage_url());
//                        }
//                    }).start();
//                }
//            }
//            @Override
//            public void onError(Throwable e) {
//            }
//        });
//    }
//    public void saveImg(String url){
//        FutureTarget<File> future = Glide.with(mContext)
//                .load(url)
//                .downloadOnly(PhoneUtils.getScreenWidth(this), PhoneUtils.getScreenHeight(this));
//        try {
//            File cacheFile = future.get();
//            String path = cacheFile.getAbsolutePath();
//            SPUtils.setPrefString(mContext,Config.imgPath,path);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//    private void getZhiFuNotifyUrl(String type) {
//        Map<String,String> map=new HashMap<String,String>();
//        map.put("payment_type",type);
//        map.put("sign", GetSign.getSign(map));
//        ApiRequest.getPayNotifyUrl(map, new MyCallBack<BaseObj>(mContext) {
//            @Override
//            public void onSuccess(BaseObj obj) {
//                if(obj.getPayment_type()==1){
//                    SPUtils.setPrefString(mContext,Config.payType_ZFB,obj.getPayment_url());
//                }else{
//                    SPUtils.setPrefString(mContext,Config.payType_WX,obj.getPayment_url());
//                }
//            }
//        });
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(myOperationBro);
        }
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            showToastS("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
