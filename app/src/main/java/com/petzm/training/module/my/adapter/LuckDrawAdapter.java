package com.petzm.training.module.my.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.petzm.training.R;
import com.petzm.training.module.my.bean.Lucky;

/**
 * Created by administartor on 2017/9/12.
 */

public class LuckDrawAdapter extends BaseRecyclerAdapter<Lucky.ImgVoBean> {


    public LuckDrawAdapter(Context mContext, int layoutId) {
        super(mContext, layoutId);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_lucky_draw, parent, false));
        return holder;

    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void bindData(RecyclerViewHolder holder, int i, Lucky.ImgVoBean bean) {
        Glide.with(mContext).load(bean.getIconUrl())
                .into(holder.getImageView(R.id.iv_lucky_draw));

//        ImageView iv_home_pp = holder.getImageView(R.id.iv_home_pp);
//        TextView tv_home_pp_more = holder.getTextView(R.id.tv_home_pp_more);
//        if(i==mList.size()){
//            iv_home_pp.setVisibility(View.GONE);
//            tv_home_pp_more.setVisibility(View.VISIBLE);
//            holder.itemView.setOnClickListener(new MyOnClickListener() {
//                @Override
//                protected void onNoDoubleClick(View view) {
//                    ActUtils.STActivity((Activity) mContext, PinPaiActivity.class);
//                }
//            });
//        }else{
//            iv_home_pp.setVisibility(View.VISIBLE);
//            tv_home_pp_more.setVisibility(View.GONE);
//            Glide.with(mContext).load(bean.getBrand_img()).error(R.color.white).into(iv_home_pp);
//            holder.itemView.setOnClickListener(new MyOnClickListener() {
//                @Override
//                protected void onNoDoubleClick(View view) {
//                    if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
//                        ActUtils.STActivity((Activity) mContext,LoginActivity.class);
//                        return;
//                    }
//                    Intent intent=new Intent();
//                    intent.putExtra(Constant.IParam.pinPaiId,bean.getBrand_id()+"");
//                    ActUtils.STActivity((Activity) mContext,intent, GoodsForPinPaiActivity.class);
//                }
//            });
//        }
    }
    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


}
