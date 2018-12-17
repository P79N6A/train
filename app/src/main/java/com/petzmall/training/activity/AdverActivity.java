package com.petzmall.training.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.petzmall.training.Config;
import com.petzmall.training.MainActivity;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseActivity;
import com.petzmall.training.module.my.activity.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/11.
 */

public class AdverActivity extends BaseActivity {
    @BindView(R.id.iv_spalsh)
    ImageView iv_spalsh;
    @BindView(R.id.tv_spalsh)
    TextView tv_spalsh;
    private String imgPath;
    private int timeLength=3;
    boolean isFirst;
    int timeCount=3;
    private Timer timer;
    private TimerTask task;
    private boolean isPreFinish;
    private String  cookie;
    @Override
    protected int getContentView() {
        return R.layout.act_adver;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imgPath = SPUtils.getPrefString(this, Config.imgPath, null);

        //TextUtils.isEmpty(imgPath)
//        if(TextUtils.isEmpty(imgPath)){
//            startActivity(new Intent(this,MainActivity.class));
//            finish();
//        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
     String   cookie1 = SPUtils.getPrefString(this,"cookie","");
        imgPath = SPUtils.getPrefString(this, Config.imgPath, null);
        cookie =  SPUtils.getPrefString(this, Config.cookie, null);
        if(!TextUtils.isEmpty(imgPath)){
            Glide.with(this).load(imgPath).into(iv_spalsh);
        }

    }

    @Override
    protected void initData() {
//        if(!TextUtils.isEmpty(imgPath)){
            setImg();
//        }
    }

    private void setImg() {
        timer = new Timer(true);

        task = new TimerTask() {
            public void run() {
                if(isPreFinish){
                    return;
                }
                if(isFirst){
                    task.cancel();
                    timer.cancel();
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("onCreate===","===444");
                            tv_spalsh.setText("跳过 "+timeCount);
                            if(cookie==null){
                                startActivity(new Intent(AdverActivity.this,LoginActivity.class));
                            }else{
                            startActivity(new Intent(AdverActivity.this,MainActivity.class));
                            }
                            finish();
                        }
                    });

                }else{
                    if(timeCount==1){
                        isFirst=true;
                    }
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            tv_spalsh.setText("跳过 "+timeCount);
                            timeCount--;
                        }
                    });

                }

            }
        };
        timer.schedule(task,0,1000);
    }

    @OnClick({R.id.tv_spalsh})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_spalsh:
                isPreFinish=true;
                if(cookie==null){
                    startActivity(new Intent(AdverActivity.this,LoginActivity.class));
                }else{
                    startActivity(new Intent(AdverActivity.this,MainActivity.class));
                }
                finish();
            break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(task!=null){
            task.cancel();
            timer.cancel();
            timer=null;
            task=null;
        }
    }


}
