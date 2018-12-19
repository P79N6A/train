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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

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
//        strings.add("http://img.d2c.cn/2018/04/13/055719886e0e44e7e6bf3f95eb80dc888e71ce.jpg");
//        strings.add("http://img.d2c.cn/2018/04/04/090731adf471f7cede663d446082a721606777.jpg");
//        strings.add("http://img.d2c.cn/2017/09/23/044820323632cb6f87d05242dc6a79a1206faa.jpg");
//        strings.add("http://img.d2c.cn/2018/03/21/0832286789d5df26025be2544401ac72b9daef.png");
//        strings.add("http://img.d2c.cn/2018/03/21/08312989dbb198ca64d3b3bd7e01ebe580e647.png");
////        strings.add("http://img.d2c.cn/2018/03/19/1101364f13d1f42fada5588a8cd0109f589710.jpg");
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

    @Override
    protected void onViewClick(View v) {

    }



}
