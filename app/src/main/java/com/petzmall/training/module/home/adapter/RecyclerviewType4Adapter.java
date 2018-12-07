package com.petzmall.training.module.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.petzmall.training.R;
import com.petzmall.training.module.home.bean.RefreshObj;

public class RecyclerviewType4Adapter extends BaseRecyclerAdapter<RefreshObj.ImgVoBean> {

    public RecyclerviewType4Adapter(Context ctx, int layoutId) {
        super(ctx, layoutId);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_home_rv6, parent, false));
        return holder;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int i, RefreshObj.ImgVoBean imgVoBean) {
        ImageView iv_home_pp = holder.getImageView(R.id.home_read_piv_iv1);
        Glide.with(mContext).load(imgVoBean.getIconUrl()).into(iv_home_pp);

        TextView title = holder.getTextView(R.id.home_tv_title1);

                title.setText("\u3000\u3000"+ imgVoBean.getNameX());
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
