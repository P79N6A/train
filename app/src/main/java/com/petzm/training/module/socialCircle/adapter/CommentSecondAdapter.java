package com.petzm.training.module.socialCircle.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.petzm.training.R;
import com.petzm.training.module.socialCircle.bean.SecondCommentBean;

/**
 * Created by administartor on 2017/9/12.
 */

public class CommentSecondAdapter extends BaseRecyclerAdapter<SecondCommentBean.DataBean.FirstVideoReplyVosBean> {


    public CommentSecondAdapter(Context mContext, int layoutId) {
        super(mContext, layoutId);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.comment_second_item_layout, parent, false));
        return holder;

    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void bindData(RecyclerViewHolder holder, int i, SecondCommentBean.DataBean.FirstVideoReplyVosBean bean) {
        Glide.with(mContext).load(bean.getFromUimg())
                .into(holder.getImageView(R.id.comment_item_logo));
        holder.setText(R.id.comment_item_userName,bean.getFromUname())
                .setText(R.id.comment_item_time,bean.getReplyTime())
//                .setText(R.id.commentTv,"共"+bean.getReplyNum()+"条回复>")
                .setText(R.id.comment_item_content,bean.getContent());
        TextView commentTv = holder.getTextView(R.id.commentTv);

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
