package com.petzmall.training.module.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.utils.ActUtils;
import com.petzmall.training.R;
import com.petzmall.training.module.home.bean.HomeObj;
import com.petzmall.training.module.home.bean.RefreshObj;

public class RecyclerviewType2Adapter extends BaseRecyclerAdapter<RefreshObj.ImgVoBean> {

    public RecyclerviewType2Adapter(Context ctx, int layoutId) {
        super(ctx, layoutId);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_raiders, parent, false));
        return holder;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int i, RefreshObj.ImgVoBean imgVoBean) {
        holder.setText(R.id.home_tv_title1,imgVoBean.getVideoVos().getVideoName())
                .setText(R.id.home_tv_title2,imgVoBean.getVideoVos().getContent());
        Glide.with(mContext).load(imgVoBean.getIconUrl()).into(holder.getImageView(R.id.home_read_piv_iv));

        holder.itemView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                ToastUtils.showToast(mContext,imgVoBean.getNameX());

//                Intent intent=new Intent();
//                intent.putExtra(Constant.IParam.pinPaiId,bean.getBrand_id()+"");
//                ActUtils.STActivity((Activity) mContext,intent, GoodsForPinPaiActivity.class);
            }
        });
    }
}
