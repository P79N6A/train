package com.petzmall.training.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.rxjava.rxbus.RxUtils;
import com.petzmall.training.GetSign;
import com.petzmall.training.MainActivity;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseActivity;
import com.petzmall.training.base.MyCallBack;
import com.petzmall.training.network.ApiRequest;
import com.petzmall.training.network.response.LoginObj;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by administartor on 2017/9/5.
 */

public class LoginPhoneActivity extends BaseActivity {

    @BindView(R.id.et_register_phone)
    EditText et_register_phone;

    @BindView(R.id.et_register_code)
    EditText et_register_code;

    @BindView(R.id.tv_register_getcode)
    TextView tv_register_getcode;

    private String smsCode="111111";

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
        setAppTitle("注册");
        setAppRightTitle("去登录");
        return R.layout.activity_login_phone;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.app_right_tv, R.id.tv_register_getcode, R.id.tv_register_commit})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.app_right_tv:
                finish();
                break;
            case R.id.tv_register_getcode:
                if (TextUtils.isEmpty(getSStr(et_register_phone))) {
                    showMsg("手机号不能为空");
                    return;
                } else if (!GetSign.isMobile(getSStr(et_register_phone))) {
                    showMsg("请输入正确手机号");
                    return;
                }
                getMsgCode();
                break;
            case R.id.tv_register_commit:
                String phone = getSStr(et_register_phone);
                String code = getSStr(et_register_code);

                if (TextUtils.isEmpty(getSStr(et_register_phone))) {
                    showMsg("手机号不能为空");
                    return;
                } else if (!GetSign.isMobile(getSStr(et_register_phone))) {
                    showMsg("请输入正确手机号");
                    return;
                }
//                else if (TextUtils.isEmpty(code) || TextUtils.isEmpty(code) || !code.equals(code)) {
//                    showMsg("请输入正确验证码");
//                    return;
//                }

                loginPhone(phone,code);
                break;
        }
    }

    private void loginPhone(String phone,String vcode) {
        showLoading();
//        STActivity(SubmitInformationActivity.class);
        Map<String,String> map = new HashMap<String,String>();
        map.put("loginPwd",vcode);
        map.put("phone",phone);
        ApiRequest.userLogin(map, new MyCallBack<LoginObj>(mContext) {
            @Override
            public void onSuccess(LoginObj obj) {
                showMsg("登陆成功");
//                finish();
                STActivity((Class) MainActivity.class);
            }


        });
    }

    private void getMsgCode() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", getSStr(et_register_phone));
        map.put("rnd", getRnd());
        String sign = GetSign.getSign(map);
        map.put("sign", sign);
        showLoading();
//        ApiRequest.getSMSCode(map, new MyCallBack<BaseObj>(mContext) {
//            @Override
//            public void onSuccess(BaseObj obj) {
//                smsCode = obj.getSMSCode();
//                countDown();
//            }
//        });

    }

    private void countDown() {
        tv_register_getcode.setEnabled(false);
        final long count = 30;
        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(31)//计时次数
                .map(integer -> count - integer)
                .compose(RxUtils.appSchedulers())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        tv_register_getcode.setEnabled(true);
                        tv_register_getcode.setText("获取验证码");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv_register_getcode.setText("剩下" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        addSubscription(subscribe);
    }
}
