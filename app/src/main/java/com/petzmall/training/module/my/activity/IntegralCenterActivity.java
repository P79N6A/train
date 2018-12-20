package com.petzmall.training.module.my.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.petzmall.training.R;
import com.petzmall.training.base.BaseActivity;
import com.petzmall.training.base.MyCallBack;
import com.petzmall.training.module.my.adapter.LuckDrawAdapter;
import com.petzmall.training.module.my.bean.Lucky;
import com.petzmall.training.module.my.network.ApiRequest;
import com.petzmall.training.view.GoodProgressView;
import com.petzmall.training.view.MyAdvertisementView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntegralCenterActivity extends BaseActivity {


    @BindView(R.id.recyclerview_lucky_draw)
    RecyclerView mRecyclerView;

    LuckDrawAdapter mAdapter;

    List<Lucky.ImgVoBean> imgVoBean;
    @BindView(R.id.good_progress_view1)
    GoodProgressView goodProgressView1;
    int progressValue = 0;
    Timer timer = new Timer();
    @BindView(R.id.recyclerview_exchange)
    RecyclerView recyclerviewExchange;
    ArrayList<String> strings = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.act_integral_center;
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
    protected void initView() {
        setAppTitle("积分中心");
        //设置布局管理器

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        //设置适配器
        mAdapter = new LuckDrawAdapter(this, 0);
        mRecyclerView.setAdapter(mAdapter);
        timer.schedule(task, 1000, 1000);

//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerviewExchange.setLayoutManager(linearLayoutManager);
//        ArrayList<String> strings = new ArrayList<>();
        strings.add("+1");
        strings.add("+2");
        strings.add("+3");
        strings.add("+4");
        strings.add("+5");
//        strings.add("http://img.d2c.cn/2018/03/19/1101364f13d1f42fada5588a8cd0109f589710.jpg");
//        MainAdapter mainAdapter = new MainAdapter(this,strings);
//        recyclerviewExchange.setAdapter(mainAdapter);
    }

    @Override
    protected void initData() {
        Map<String, String> map = new HashMap<String, String>();
        ApiRequest.getLuckdraw(map, new MyCallBack<Lucky>(mContext) {
            @Override
            public void onSuccess(Lucky obj) {
                imgVoBean = obj.getImgVo();
                mAdapter.setList(obj.getImgVo(), true);
            }
        });
    }


    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Log.i("log", "handler : progressValue=" + progressValue);

                //通知view，进度值有变化
                goodProgressView1.setProgressValue(progressValue * 1);
                goodProgressView1.postInvalidate();

                progressValue += 1;
                if (progressValue > 10) {
                    timer.cancel();
                }
            }
            super.handleMessage(msg);
        }

        ;
    };

    @OnClick({ R.id.ll_jifen_task})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.ll_jifen_task:
                MyAdvertisementView myAdvertisementView = new MyAdvertisementView(this,R.style.dialog,strings);
                myAdvertisementView.setCancelable(false);
                myAdvertisementView.showDialog();
                break;
        }
    }


}
