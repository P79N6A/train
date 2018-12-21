package com.petzm.training.module.my.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.google.gson.Gson;
import com.petzm.training.Config;
import com.petzm.training.GetSign;
import com.petzm.training.MainActivity;
import com.petzm.training.R;
import com.petzm.training.base.BaseActivity;
import com.petzm.training.network.response.LoginObj;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/31.
 */

public class LoginActivity extends BaseActivity {


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected int getContentView() {
        return R.layout.act_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick({ R.id.iv_login_wechat,R.id.iv_login_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_phone:
                STActivity(LoginPhoneActivity.class);
                break;
            case R.id.iv_login_wechat:
                if (!UMShareAPI.get(mContext).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    showMsg("请安装微信之后再试");
                    return;
                }
                UMShareConfig config = new UMShareConfig();
                config.isNeedAuthOnGetUserInfo(true);
                UMShareAPI.get(this).setShareConfig(config);
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        showLoading();
                        Log.i("========","onStart=");
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> m) {
                        dismissLoading();
                        Log.i("========","onComplete="+new Gson().toJson(m).toString());
                        String unionid = m.get("unionid");
                        String name = m.get("name");
                        String profile_image_url = m.get("profile_image_url");
                        loginForApp("2",unionid, name, profile_image_url);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        dismissLoading();
                        showMsg("微信登录失败"+"onError="+throwable.getMessage());
                        Log.i("========","onError="+throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        dismissLoading();
                        Log.i("========","onCancel=");
                    }
                });
                break;
        }
    }
    private void loginForApp(String platform,String unionid, String name, String profile_image_url) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("platform",platform);
        map.put("open",unionid);
        map.put("nickname",name);
        map.put("avatar",profile_image_url);
        map.put("RegistrationID", SPUtils.getPrefString(mContext, Config.jiguangRegistrationId,""));
        map.put("sign", GetSign.getSign(map));

        SPUtils.setPrefString(mContext, Config.avatar,profile_image_url);
        SPUtils.setPrefString(mContext, Config.nick_name,name);



        finish();

//        ApiRequest.platformLogin(map, new MyCallBack<LoginObj>(mContext) {
//            @Override
//            public void onSuccess(LoginObj obj) {
//                loginResult(obj);
//            }
//        });

    }


    private void loginResult(LoginObj obj) {
//        SPUtils.setPrefString(mContext, Config.user_id,obj.getUser_id());
//        SPUtils.setPrefString(mContext, Config.mobile,obj.getMobile());
//        SPUtils.setPrefString(mContext, Config.sex,obj.getSex());
//        SPUtils.setPrefString(mContext, Config.avatar,obj.getAvatar());
//        SPUtils.setPrefString(mContext, Config.birthday,obj.getBirthday());
//        SPUtils.setPrefString(mContext, Config.nick_name,obj.getNick_name());
//        SPUtils.setPrefString(mContext, Config.user_name,obj.getUser_name());
//        SPUtils.setPrefString(mContext, Config.amount,obj.getAmount()+"");
//        SPUtils.setPrefInt(mContext, Config.count_wsy,obj.getCount_wsy());
//        SPUtils.setPrefInt(mContext, Config.keeping_bean,obj.getKeeping_bean());
//        SPUtils.setPrefInt(mContext, Config.news_is_check,obj.getNews_is_check());
//        SPUtils.setPrefBoolean(mContext, Config.user_switch, obj.getMessage_sink()==1?true:false);
//        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.Bro.operation));
        STActivity(MainActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.i("=======","onActivityResult======");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
