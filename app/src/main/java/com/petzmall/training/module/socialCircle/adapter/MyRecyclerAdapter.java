package com.petzmall.training.module.socialCircle.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.petzmall.training.R;
import com.petzmall.training.constants.P;
import com.petzmall.training.listener.OnItemPictureClickListener;
import com.petzmall.training.module.home.bean.RefreshObj;
import com.petzmall.training.module.socialCircle.activity.ImagePreviewActivity;
import com.petzmall.training.view.MultiImageView;
import com.petzmall.training.view.PhotoInfo;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RefreshObj>  datas;
    private Context context;

    public final static int TYPE_NORMAL = 1;//Item的第一种布局
    public final static int TYPE_PICTURE = 2;//Item的第二种布局


    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private OnItemPictureClickListener listener;

    public MyRecyclerAdapter(ArrayList<RefreshObj> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public MyRecyclerAdapter(ArrayList<RefreshObj> datas, Context context, boolean hasMore,OnItemPictureClickListener listener) {
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View view;
        switch (viewType) {
            default:
            case TYPE_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle1_moment, parent, false);
                vh = new MyRecyclerAdapter.NormalHolder(view);
                return vh;
//            case TYPE_NORTWO:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_recycler, parent, false);
//                vh = new MyRecyclerAdapter.TwoHolder(view);
//                return vh;
            case TYPE_PICTURE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footview, parent, false);
                vh = new MyRecyclerAdapter.FootHolder(view);
                return vh;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalHolder) {
            List<PhotoInfo> list = new ArrayList<>();
            List<String> picList = new ArrayList<>();
            for(int j = 0;j<datas.get(position).getImgVoX().size();j++){
                PhotoInfo photoInfo = new PhotoInfo();
                photoInfo.setUrl(datas.get(position).getImgVoX().get(j).getIconUrl());
                list.add(photoInfo);
                picList.add((datas.get(position).getImgVoX().get(j).getIconUrl()));
            }
            if (list != null && list.size() > 0) {
                ((NormalHolder)holder).multiImagView.setVisibility(View.VISIBLE);
                ((NormalHolder)holder).multiImagView.setList(list);
                ((NormalHolder)holder).multiImagView.setOnItemClickListener((view, position1, url, imagesList, imageView) -> {
//                        itemPosition = item;
                    Intent intent = new Intent(context, ImagePreviewActivity.class);
                    view.setTransitionName("aaa");
                    intent.putStringArrayListExtra("imageList", (ArrayList<String>) picList);
                    intent.putExtra(P.START_ITEM_POSITION, position1);
                    intent.putExtra(P.START_IAMGE_POSITION, position1);
                    ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation((Activity) context,view,"aaa");
                    ActivityCompat.startActivity(context,intent, compat.toBundle());
                });
            } else {
                ((NormalHolder)holder).multiImagView.setVisibility(View.GONE);
            }

//            ((NormalHolder) holder).nineGridTestLayout.setUrlList(list);
        } else {

            Glide.with(context).load(datas.get(position).getThemeIdX()).into(((FootHolder) holder).imageView);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public int getRealLastPosition() {
        return datas.size();
    }


    public void updateList(ArrayList<RefreshObj> newDatas, boolean hasMore) {
        if (newDatas != null) {
            datas.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private MultiImageView multiImagView;
        public NormalHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_my_name);
            multiImagView = (MultiImageView)itemView.findViewById(R.id.multiImagView);
        }
    }



    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;
        private ImageView imageView;
        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
            imageView = itemView.findViewById(R.id.iv);
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
        if (datas.get(position).getTypeX() == 2) {
            return TYPE_PICTURE;
        } else if(datas.get(position).getTypeX() == 1){
            return TYPE_NORMAL;
        }else{
            return 3;
        }
    }
}
