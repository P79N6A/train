package com.petzmall.training.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.petzmall.training.R;
import com.petzmall.training.module.my.adapter.DialogAdapter;
import com.petzmall.training.module.my.adapter.LuckDrawAdapter;
import com.petzmall.training.module.my.bean.Lucky;

import java.util.ArrayList;
import java.util.List;

public class MyAdvertisementView extends Dialog implements View.OnClickListener {

    DialogAdapter mAdapter;
    Context context;
    ArrayList<String> strings;
    public MyAdvertisementView(Context context) {
        super(context);

    }

    public MyAdvertisementView(Context context, int theme,ArrayList<String> strings) {
        super(context, theme);
        this.context= context;
        this.strings =strings;
        setContentView(R.layout.view_dialog);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(true);
    }
    public void showDialog() {
        Window window = getWindow();
        //设置弹窗动画
        window.setWindowAnimations(R.style.popupAnimation);
        //设置Dialog背景色

//        window.setBackgroundDrawableResource(R.color.red);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置弹窗位置
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
        show();
        RecyclerView mRecyclerView =  findViewById(R.id.dialog_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 5));
        //设置适配器
        mAdapter = new DialogAdapter(context, 0);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setList(strings, true);
        findViewById(R.id.iv_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
