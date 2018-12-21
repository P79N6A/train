package com.petzm.training.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.petzm.training.R;
import com.petzm.training.module.my.adapter.DialogAdapter;

import java.util.ArrayList;

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

       TextView text =  findViewById(R.id.dialog_title);//dialog title
        String str1 = "今日已签到成功";
        String str2 = "!";

        SpannableStringBuilder builder = new SpannableStringBuilder(str1 + str2);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#F62E5E")),
                str1.length(), (str1 + str2).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);


        text.setText(builder);
        findViewById(R.id.iv_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
