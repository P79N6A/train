//package com.petzmall.training.module.course.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.github.androidtools.PhoneUtils;
//import com.github.androidtools.inter.MyOnClickListener;
//import com.github.baseclass.adapter.BaseRecyclerAdapter;
//import com.github.baseclass.adapter.RecyclerViewHolder;
//import com.github.baseclass.utils.ActUtils;
//import com.petzmall.training.R;
//import com.petzmall.training.module.home.bean.UserFavouriteItemEntity;
///**
// * Created by Administrator on 2018/4/23.
// */
//
//public class GoodPraiseAdatpter extends BaseRecyclerAdapter<UserFavouriteItemEntity> {
//
//    private int screenWidth;
//
//    public GoodPraiseAdatpter(Context mContext, int layoutId) {
//        super(mContext, layoutId);
//    }
//
//    @Override
//    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_recyclerview_channel, parent, false));
//        return holder;
//
//    }
//    @Override
//    public void bindData(RecyclerViewHolder holder, int i, UserFavouriteItemEntity userFavouriteItemEntity) {
//        screenWidth = PhoneUtils.getScreenWidth(mContext);
//        int imgWidth = (screenWidth - 2) / 2 - PhoneUtils.dip2px(mContext, 0);
//        ImageView iv_goods_img = holder.getImageView(R.id.tv_courses_name);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.width=imgWidth;
//        layoutParams.height=imgWidth/2;
//        iv_goods_img.setLayoutParams(layoutParams);
//        Glide.with(mContext).load(userFavouriteItemEntity.getUrl()).into(iv_goods_img);
//    }
//    @Override
//    public int getItemCount() {
//        return mList==null?0:mList.size();
//    }
//}
