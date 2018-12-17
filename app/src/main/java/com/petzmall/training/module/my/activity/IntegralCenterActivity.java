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
    int progressValue=0;
    Timer timer = new Timer();
    @Override
    protected int getContentView() {
        return R.layout.act_integral_center;
    }

    @Override
    protected void initView() {

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        //设置适配器
        mAdapter = new LuckDrawAdapter(this, 0);
        mRecyclerView.setAdapter(mAdapter);
        timer.schedule(task, 1000, 1000);




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
                Log.i("log","handler : progressValue="+progressValue);

                //通知view，进度值有变化
                goodProgressView1.setProgressValue(progressValue*1);
                goodProgressView1.postInvalidate();



                progressValue+=1;
                if(progressValue>10){
                    timer.cancel();
                }
            }
            super.handleMessage(msg);
        };
    };

    @Override
    protected void onViewClick(View v) {

    }


}
