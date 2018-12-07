package com.petzmall.training.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.github.androidtools.ClickUtils;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.baseclass.fragment.IBaseFragment;
import com.github.baseclass.rx.RxBus;
//import com.hyphenate.chat.ChatClient;
//import com.hyphenate.helpdesk.callback.Callback;
//import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.gyf.barlibrary.ImmersionBar;
import com.petzmall.training.Config;
import com.petzmall.training.GetSign;
import com.petzmall.training.R;
//import com.petzmall.training.service.LocationServices;
import com.petzmall.training.listener.PermissionListener;
import com.petzmall.training.view.ProgressLayout;
//import com.sk.yangyu.Config;
//import com.sk.yangyu.GetSign;
//import com.sk.yangyu.module.my.network.ApiRequest;
//import com.sk.yangyu.module.my.network.response.FenXiangObj;
//import com.sk.yangyu.view.ProgressLayout;
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.media.UMWeb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;


/**
 * Created by Administrator on 2017/7/13.
 */

public abstract class BaseFragment extends IBaseFragment implements View.OnClickListener,ProgressLayout.OnAgainInter{
    protected int pageNum=2;
    protected int pageSize=20;

    private boolean isFirst = true;
    private boolean isPrepared;
    protected PtrClassicFrameLayout pcfl;
    private RequestPermissionCallBack mRequestPermissionCallBack;
    private final int mRequestCode = 1024;

    protected Activity mActivity;
    protected Unbinder mUnBind;

    /************************************************/
    protected abstract int getContentView();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void onViewClick(View v);
    protected void initRxBus(){};
    protected boolean isPause;

    protected void myReStart() {
    }

    protected ProgressLayout pl_load;//进度框

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        mUnBind = ButterKnife.bind(this, view);
        return view;
    }


    private void lazyLoad() {
        if (!isFirst || !isPrepared) {
            return;
        }
        isFirst = false;
        initData();
    }
    private void resetState() {
        isFirst = true;
        isPrepared = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(null!=view.findViewById(R.id.pcfl_refresh)){
            pcfl = (PtrClassicFrameLayout) view.findViewById(R.id.pcfl_refresh);
            pcfl.setLastUpdateTimeRelateObject(this);
            pcfl.setOffsetXFlag(3);
            pcfl.setHorizontalMoveFlag(3);
            pcfl.setScaledTouchSlopFlag(1);
            pcfl.disableWhenHorizontalMove(true);
        }
        if(null!=view.findViewById(R.id.pl_load)){
            pl_load = (ProgressLayout) view.findViewById(R.id.pl_load);
            pl_load.setInter(this);
        }
        initView();
        initRxBus();
//        isPrepared=true;
//        setUserVisibleHint(true);
    }


    /**
     * 找到activity的控件
     *
     * @param <T> the type parameter
     * @param id  the id
     * @return the
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findActivityViewById(@IdRes int id) {
        return (T) mActivity.findViewById(id);
    }


    public void showProgress(){
        if (pl_load != null) {
            pl_load.showProgress();
        }
    }
    public void showContent(){
        if (pl_load != null) {
            pl_load.showContent();
        }
    }
    public void showErrorText(){
        if (pl_load != null) {
            pl_load.showErrorText();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        isPause =true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            isPause =true;
        }else{
            isPause =false;
            myReStart();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(isPause){
            isPause =false;
            myReStart();
        }
    }

    protected String getSStr(View view){
        if(view instanceof TextView){
            return ((TextView)view).getText().toString();
        } else if (view instanceof EditText) {
            return ((EditText)view).getText().toString();
        }else{
            return null;
        }
    }
    @Override
    public void onClick(View v) {
        if(!ClickUtils.isFastClick(v)){
            onViewClick(v);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetState();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBind.unbind();
        RxBus.getInstance().removeAllStickyEvents();
    }

    @Override
    public void again() {
        initData();
    }
    public int getUserType(){
        return SPUtils.getPrefInt(mContext, Config.userType,-1);
    }
    protected String getUserId(){
        return SPUtils.getPrefString(mContext,Config.user_id,null);
    }
    protected String getUsersId(){
        return SPUtils.getPrefString(mContext,Config.user_id,"0");
    }
    protected String getSign(){
        return getSign("user_id",getUserId());
    }
    protected String getSign(String key,String value){
        return GetSign.getSign(key,value);
    }
    protected boolean isEmpty(List list){
        return list == null || list.size() == 0;
    }
    protected boolean notEmpty(List list){
        return !(list == null || list.size() == 0);
    }
    protected String getRnd(){
        Random random = new Random();
        int rnd = random.nextInt(9000) + 1000;
        return rnd+"";
    }
    protected BaseDividerListItem getItemDivider(){
        return new BaseDividerListItem(mContext,BaseDividerListItem.VERTICAL_LIST,2,R.color.background_f2);
    }
    protected BaseDividerListItem getItemDivider(int height){
        return new BaseDividerListItem(mContext,BaseDividerListItem.VERTICAL_LIST,height,R.color.background_f2);
    }
    protected BaseDividerListItem getItemDivider(int height,int color){
        return new BaseDividerListItem(mContext,BaseDividerListItem.VERTICAL_LIST,height,color);
    }


    protected void initWebViewForContent(WebView webview, String content) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        //自适应屏幕  
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webview.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        //设置Web视图
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                initWebTopView();
            }
        });

        webview.loadDataWithBaseURL(null, getNewContent(content), "text/html", "utf-8",null);
//        webview.loadUrl(url);
        // 设置WevView要显示的网页
//        webview.loadDataWithBaseURL(null, content, "text/html", "utf-8",null);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webview.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
    }
    protected void initWebViewForUrl(WebView webview,String url) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        //自适应屏幕  
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webview.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        //设置Web视图
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                initWebTopView();
            }
        });

//        webview.loadDataWithBaseURL(null, getNewContent(url), "text/html", "utf-8",null);
        webview.loadUrl(url);
        // 设置WevView要显示的网页
//        webview.loadDataWithBaseURL(null, content, "text/html", "utf-8",null);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webview.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
    }


    protected static String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }


    /**
     * 权限请求结果回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        StringBuilder  permissionName = new StringBuilder();
        for (String s: permissions) {
            permissionName = permissionName.append(s + "\r\n");
        }
        switch (requestCode) {
            case mRequestCode: {
                for (int i = 0; i < grantResults.length; ++i) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        hasAllGranted = false;
                        //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                        // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
                            new AlertDialog.Builder(getActivity())
                                    .setMessage("【用户选择了不在提示按钮，或者系统默认不在提示（如MIUI）。" +
                                            "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】\r\n" +
                                            "获取相关权限失败:\r\n" +
                                            permissionName +
                                            "将导致部分功能无法正常使用，需要到设置页面手动授权")
                                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                            intent.setData(uri);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mRequestPermissionCallBack.denied();
                                        }
                                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    mRequestPermissionCallBack.denied();
                                }
                            }).show();

                        } else {
                            //用户拒绝权限请求，但未选中“不再提示”选项
                            mRequestPermissionCallBack.denied();
                        }
                        break;
                    }
                }
                if (hasAllGranted) {
                    mRequestPermissionCallBack.granted();
                }
            }
        }
    }

    /**
     * 发起权限请求
     *
     * @param context
     * @param permissions
     * @param callback
     */
    public void requestPermissions(final Context context, final String[] permissions,
                                   RequestPermissionCallBack callback) {
        this.mRequestPermissionCallBack = callback;
        StringBuilder permissionNames = new StringBuilder();
        for(String s : permissions){
            permissionNames = permissionNames.append(s + "\r\n");
        }
        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
        boolean isAllGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                isAllGranted = false;

                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                    new AlertDialog.Builder(context)
                            .setMessage("【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】\r\n" +
                                    "您好，需要如下权限：\r\n" +
                                    permissionNames+
                                    " 请允许，否则将影响部分功能的正常使用。")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
                                }
                            }).show();
                } else {
                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
                }

                break;
            }
        }
        if (isAllGranted) {
            mRequestPermissionCallBack.granted();
            return;
        }
    }

    /**
     * 权限请求结果回调接口
     */
  public   interface RequestPermissionCallBack {
        /**
         * 同意授权
         */
        public void granted();

        /**
         * 取消授权
         */
        public void denied();
    }



//    public void goHX(){
//        if(ChatClient.getInstance().isLoggedInBefore()){
//            //已经登录，可以直接进入会话界面
//            OpenHuanXin();
//        }else{
//            showLoading();
//            hxName=SPUtils.getPrefString(mContext,Config.phone,null);
//            if(TextUtils.isEmpty(hxName)){
//                Random random = new Random();
//                int rn1=random.nextInt(9000) + 1000;
//                int rn2=random.nextInt(9000) + 1000;
//                hxName=rn1+""+rn2;
//                ChatClient.getInstance().createAccount(hxName, "123456", new Callback(){
//                    @Override
//                    public void onSuccess() {
//                        loginHXSuccess(hxName);
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
//            }else{
//                loginHXSuccess(hxName);
//            }
//        }
//    }
    String hxName;
//    private void loginHXSuccess(String hxName) {
//        //未登录，需要登录后，再进入会话界面
//        ChatClient.getInstance().login(hxName,"123456", new Callback(){
//            @Override
//            public void onSuccess() {
//                dismissLoading();
//                OpenHuanXin();
//            }
//            @Override
//            public void onError(int i, String s) {
//                dismissLoading();
//
//            }
//            @Override
//            public void onProgress(int i, String s) {
//            }
//        });
//    }
//    private void OpenHuanXin() {
//        Intent intent = new IntentBuilder(mContext)
//                .setServiceIMNumber(Config.hx_fwh) //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
//                .build();
//        startActivity(intent);
//    }
//    /*****************************************************************第三方分享********************************************************************************/
//    protected void fenXiang(SHARE_MEDIA shareMedia,String  fenXiangId) {
//        showLoading();
//        Map<String,String> map=new HashMap<String,String>();
//        map.put("goods_id",fenXiangId);
//        map.put("sign",GetSign.getSign(map));
//        ApiRequest.fenXiang(map, new MyCallBack<FenXiangObj>(mContext,true) {
//            @Override
//            public void onSuccess(FenXiangObj obj) {
//                UMWeb web = new UMWeb(obj.getShare_link());
//                UMImage image=new UMImage(mContext,R.drawable.app_default);
//                web.setTitle(obj.getTitle());//标题
//                web.setThumb(image);  //缩略图
//                web.setDescription(obj.getContent());//描述
//                new ShareAction(mContext)
//                        .setPlatform(shareMedia)//传入平台
//                        .withMedia(web)
////                      .withText(getSStr(tv_fenxiao_detail_code))//分享内容
//                        .setCallback(getListener())
//                        .share();
//            }
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                dismissLoading();
//            }
//        });
//    }
//    protected UMShareListener getListener() {
//        return new UMShareListener() {
//            @Override
//            public void onStart(SHARE_MEDIA share_media) {
//                dismissLoading();
//                Log.i("============","============onStart");
//            }
//
//            @Override
//            public void onResult(SHARE_MEDIA share_media) {
//                Log.i("============","============onResult");
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                showMsg(throwable.getMessage());
//                Log.i("============","============onError");
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA share_media) {
//                Log.i("============","============onCancel");
//            }
//        };
//    }
//    BottomSheetDialog fenXiangDialog;
//    public void showFenXiang(String fenXiangId){
//        if (fenXiangDialog == null) {
//            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_fen_xiang,null);
//            /*iv_yaoqing_wx
//iv_yaoqing_friend
//iv_yaoqing_qq
//iv_yaoqing_qzone
//iv_yaoqing_sina*/
//            sexView.findViewById(R.id.iv_yaoqing_wx).setOnClickListener(new MyOnClickListener() {
//                @Override
//                protected void onNoDoubleClick(View view) {
//                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
//                        showMsg("请安装微信之后再试");
//                        return;
//                    }
//                    fenXiang(SHARE_MEDIA.WEIXIN,fenXiangId);
//                    fenXiangDialog.dismiss();
//
//                }
//            });
//            sexView.findViewById(R.id.iv_yaoqing_friend).setOnClickListener(new MyOnClickListener() {
//                @Override
//                protected void onNoDoubleClick(View view) {
//                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
//                        showMsg("请安装微信之后再试");
//                        return;
//                    }
//                    fenXiang(SHARE_MEDIA.WEIXIN_CIRCLE,fenXiangId);
//                    fenXiangDialog.dismiss();
//
//                }
//            });
//            sexView.findViewById(R.id.iv_yaoqing_qq).setOnClickListener(new MyOnClickListener() {
//                @Override
//                protected void onNoDoubleClick(View view) {
//                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
//                        showMsg("请安装QQ之后再试");
//                        return;
//                    }
//                    fenXiang(SHARE_MEDIA.QQ,fenXiangId);
//                    fenXiangDialog.dismiss();
//                }
//            });
//            sexView.findViewById(R.id.iv_yaoqing_qzone).setOnClickListener(new MyOnClickListener() {
//                @Override
//                protected void onNoDoubleClick(View view) {
//                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
//                        showMsg("请安装QQ之后再试");
//                        return;
//                    }
//                    fenXiang(SHARE_MEDIA.QZONE,fenXiangId);
//                    fenXiangDialog.dismiss();
//                }
//            });
//           /* sexView.findViewById(R.id.iv_yaoqing_sina).setOnClickListener(new MyOnClickListener() {
//                @Override
//                protected void onNoDoubleClick(View view) {
//                    showMsg("正在开发中");
//                    fenXiangDialog.dismiss();
//                }
//            });*/
//            sexView.findViewById(R.id.tv_fenxiang_cancle).setOnClickListener(new MyOnClickListener() {
//                @Override
//                protected void onNoDoubleClick(View view) {
//                    fenXiangDialog.dismiss();
//                }
//            });
//            fenXiangDialog=new BottomSheetDialog(mContext);
//            fenXiangDialog.setCanceledOnTouchOutside(true);
//            fenXiangDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            fenXiangDialog.setContentView(sexView);
//        }
//        fenXiangDialog.show();
//    }
    public boolean keJian(View view){
        int screenWidth= PhoneUtils.getScreenWidth(mContext);
        int screenHeight=PhoneUtils.getScreenHeight(mContext);

        Rect rect=new Rect(0,0,screenWidth,screenHeight );
        int[] location = new int[2];
        view.getLocationInWindow(location);
        System.out.println(Arrays.toString(location));
        // Rect ivRect=new Rect(imageView.getLeft(),imageView.getTop(),imageView.getRight(),imageView.getBottom());
        if (view.getLocalVisibleRect(rect)) {/*rect.contains(ivRect)*/
            return true;
        } else {
            return false;
        }
    }

}
