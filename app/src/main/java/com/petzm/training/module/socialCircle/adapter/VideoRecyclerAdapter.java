package com.petzm.training.module.socialCircle.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.petzm.training.R;
import com.petzm.training.module.socialCircle.bean.Video;
import com.petzm.training.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Video.VideolistBean>  datas;
    private Context mContext;

    public final static int TYPE_FOOTER = 10;//加载更多的布局，进度条显示

    public final static int TYPE_NORMAL2 = 2;//Item的第3种布局
    private LayoutInflater inflater;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public VideoRecyclerAdapter(ArrayList<Video.VideolistBean> datas, Context context, boolean hasMore) {
        this.datas = datas;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);

        this.hasMore = hasMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View view;
        switch (viewType) {
            default:
            case TYPE_NORMAL2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_rv, parent, false);
                vh = new VideoRecyclerAdapter.TypetypeHolder3(view);
                return vh;

        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypetypeHolder3) {
            initCenter1Bean((VideoRecyclerAdapter.TypetypeHolder3) holder, datas, position);
        } else {

        }

    }

    private void initCenter1Bean(VideoRecyclerAdapter.TypetypeHolder3 holder, List<Video.VideolistBean> imgVoBeans, int position) {
        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, 1));
        RecyclerviewVideoAdapter  recyclerviewType3Adapter=new RecyclerviewVideoAdapter(mContext,0);
        holder.rvtype.setAdapter(recyclerviewType3Adapter);
        recyclerviewType3Adapter.setList(imgVoBeans,true);
        holder.rvtype.setItemAnimator(null);
        holder.rvtype.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 1)));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void updateList(ArrayList<Video.VideolistBean> newDatas, boolean hasMore) {
        if (newDatas != null) {
            datas.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }






    public class TypetypeHolder3 extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_homepageradapter_artist)
        RecyclerView rvtype;

        public TypetypeHolder3(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



    class FootHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tips)
        TextView tips;

        public FootHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public boolean isFadeTips() {
        return fadeTips;
    }

    public void resetDatas() {
        datas = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return TYPE_NORMAL2;
//        if(datas.get(position).getTypeX() == 1){
//            return TYPE_BANNER;
//        }else if(datas.get(position).getTypeX() == 2){
//            return TYPE_NORMAL1;
//        }else if(datas.get(position).getTypeX() == 3){
//            return TYPE_NORMAL2;
//        }else if(datas.get(position).getTypeX() == 4){
//            return TYPE_NORMAL3;
//        }else if(position == getItemCount() - 1){
//            return TYPE_FOOTER;
//        }else{
//            return 0;
//        }

//        if (position == getItemCount() - 1) {
//            return TYPE_FOOTER;
//        } else if(datas.get(position).getType() == 1){
//            return TYPE_BANNER;
//        }else if(datas.get(position).getType() == 2){
//            return TYPE_NORMAL1;
//        }else{
//            return TYPE_FOOTER;
//        }
    }
}
