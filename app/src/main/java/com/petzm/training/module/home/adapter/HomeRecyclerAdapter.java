package com.petzm.training.module.home.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.ToastUtils;
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyImageView;
import com.github.customview.MyTextView;
import com.petzm.training.R;
import com.petzm.training.base.MyCallBack;
import com.petzm.training.module.home.bean.RefreshObj;
import com.petzm.training.module.home.event.RefreshEvent;
import com.petzm.training.module.home.network.ApiRequest;
import com.petzm.training.tools.GlideLoader;
import com.petzm.training.view.DividerGridItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RefreshObj>  datas;
    private Context mContext;

    public final static int TYPE_FOOTER = 10;//加载更多的布局，进度条显示

    public final static int TYPE_BANNER = 0;//Item的第1种布局
    public final static int TYPE_NORMAL1 = 1;//Item的第2种布局
    public final static int TYPE_NORMAL2 = 2;//Item的第3种布局
    public final static int TYPE_NORMAL3 = 3;//Item的第4种布局
    private LayoutInflater inflater;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public HomeRecyclerAdapter(ArrayList<RefreshObj> datas, Context context, boolean hasMore) {
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
            case TYPE_BANNER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_banner, parent, false);
                vh = new HomeRecyclerAdapter.BannerHolder(view);
                return vh;
            case TYPE_NORMAL1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_rv2, parent, false);
                vh = new HomeRecyclerAdapter.TypetypeHolder2(view);
                return vh;
            case TYPE_NORMAL2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_rv3, parent, false);
                vh = new HomeRecyclerAdapter.TypetypeHolder3(view);
                return vh;
            case TYPE_NORMAL3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_rv2, parent, false);
                vh = new HomeRecyclerAdapter.TypetypeHolder4(view);
                return vh;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerHolder) {
            initslider((BannerHolder)holder, datas.get(position).getImgVoX());
        }else if(holder instanceof TypetypeHolder2){
            initCenterBean((HomeRecyclerAdapter.TypetypeHolder2) holder,datas.get(position).getImgVoX(),datas.get(position).getNameX(),position);//加载中间head下面的数据源
        }else if(holder instanceof TypetypeHolder3){
            initCenter1Bean((HomeRecyclerAdapter.TypetypeHolder3) holder,datas.get(position).getImgVoX(),datas.get(position).getNameX(),position);//加载中间head下面的数据源
        }else if(holder instanceof TypetypeHolder4){
            initCenter2Bean((HomeRecyclerAdapter.TypetypeHolder4) holder,datas.get(position).getImgVoX(),datas.get(position).getNameX(),position);//加载中间head下面的数据源
        }else {
        }

    }



    private void initslider(BannerHolder holder, List<RefreshObj.ImgVoBean> data) {
        List<String>bannerList = new ArrayList<String>();
        for( RefreshObj.ImgVoBean url :data){
            bannerList.add(url.getIconUrl());
        }
        Banner banner = holder.banner;
        banner.setIndicatorGravity(BannerConfig.RIGHT);//设置指示器位置
        banner.setDelayTime(5000);//设置轮播时间
        banner.setImages(bannerList);
        banner.setImageLoader(new GlideLoader());
        banner.start();
    }

    private void initCenterBean(HomeRecyclerAdapter.TypetypeHolder2 holder, List<RefreshObj.ImgVoBean> imgVoBeans, String title, int position) {
        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, 2));
        holder.tvTitle.setText(title);
        RecyclerviewType2Adapter   recyclerviewType2Adapter=new RecyclerviewType2Adapter(mContext,0);
        holder.rvtype.setAdapter(recyclerviewType2Adapter);
        recyclerviewType2Adapter.setList(imgVoBeans,true);
        holder.rvtype.setItemAnimator(null);
        holder.changeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<String, String>();
                ApiRequest.getRefreshData(map, new MyCallBack<RefreshObj>(mContext,true) {
                    @Override
                    public void onSuccess(RefreshObj obj) {
                        ToastUtils.showToast(mContext,position+"");
                        recyclerviewType2Adapter.setList(obj.getImgVoX(),true);
                        holder.tvTitle.setText(obj.getNameX());
                        RxBus.getInstance().post(new RefreshEvent(position));
                    }


                });
            }
        });
    }

    private void initCenter1Bean(HomeRecyclerAdapter.TypetypeHolder3 holder, List<RefreshObj.ImgVoBean> imgVoBeans, String title, int position) {
        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, 1));
        holder.tvTitle.setText(title);
        Glide.with(mContext).load(imgVoBeans.get(0).getIconUrl()).into(holder.homeReadPivIv1);
        holder.homeTvTitle1.setText(imgVoBeans.get(0).getNameX());
        holder.homeTvTitle2.setText(imgVoBeans.get(0).getNameX());
        holder.tvVideoNum.setText(imgVoBeans.get(0).getNameX());
        holder.tvVideoTime.setText(imgVoBeans.get(0).getNameX());
        holder.tvVideoAuthor.setText(imgVoBeans.get(0).getNameX());

        Glide.with(mContext).load(imgVoBeans.get(1).getIconUrl()).into(holder.homeReadPivIv2);
        holder.homeTvRightTitle1.setText(imgVoBeans.get(1).getNameX());
        holder.homeTvRightTitle2.setText(imgVoBeans.get(1).getNameX());
        holder.tvRightVideoNum.setText(imgVoBeans.get(1).getNameX());
        holder.tvRightVideoTime.setText(imgVoBeans.get(1).getNameX());
        holder.tvRightVideoAuthor.setText(imgVoBeans.get(1).getNameX());

        RecyclerviewType3Adapter   recyclerviewType3Adapter=new RecyclerviewType3Adapter(mContext,0);
        holder.rvtype.setAdapter(recyclerviewType3Adapter);
        List<RefreshObj.ImgVoBean>  bean = new ArrayList<>();
        for(int i = 2;i < imgVoBeans.size(); i ++){
            bean.add(imgVoBeans.get(i));
        }
        recyclerviewType3Adapter.setList(bean,true);
        holder.rvtype.setItemAnimator(null);
        holder.rvtype.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 1)));
        holder.changeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<String, String>();
                ApiRequest.getRefreshData(map, new MyCallBack<RefreshObj>(mContext,true) {
                    @Override
                    public void onSuccess(RefreshObj obj) {
                        ToastUtils.showToast(mContext,position+"");
                        recyclerviewType3Adapter.setList(obj.getImgVoX(),true);
                        holder.tvTitle.setText(obj.getNameX());
                        RxBus.getInstance().post(new RefreshEvent(position));
                    }


                });
            }
        });
    }

    private void initCenter2Bean(HomeRecyclerAdapter.TypetypeHolder4 holder, List<RefreshObj.ImgVoBean> imgVoBeans, String title, int position) {
        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, 1));
        holder.tvTitle.setText(title);
        RecyclerviewType4Adapter  recyclerviewType4Adapter=new RecyclerviewType4Adapter(mContext,0);
        holder.rvtype.setAdapter(recyclerviewType4Adapter);
        holder.rvtype.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 1)));
        recyclerviewType4Adapter.setList(imgVoBeans,true);
        holder.rvtype.setItemAnimator(null);
        holder.changeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<String, String>();
                ApiRequest.getRefreshData(map, new MyCallBack<RefreshObj>(mContext,true) {
                    @Override
                    public void onSuccess(RefreshObj obj) {
                        ToastUtils.showToast(mContext,position+"");
                        recyclerviewType4Adapter.setList(obj.getImgVoX(),true);
                        holder.tvTitle.setText(obj.getNameX());
                        RxBus.getInstance().post(new RefreshEvent(position));
                    }


                });
            }
        });

    }
    @Override
    public int getItemCount() {
        return datas!=null?datas.size():0;
    }

    public void updateList(ArrayList<RefreshObj> newDatas, boolean hasMore) {
        if (newDatas != null) {
            datas.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }


    class BannerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        Banner banner;
        public BannerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public class TypetypeHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_homepageradapter_artist)
        RecyclerView rvtype;
        @BindView(R.id.tv_title)
        MyTextView tvTitle;
        @BindView(R.id.change_content)
        MyTextView changeContent;//换一换


        public TypetypeHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class TypetypeHolder3 extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_homepageradapter_artist)
        RecyclerView rvtype;
        @BindView(R.id.tv_title)
        MyTextView tvTitle;
        @BindView(R.id.change_content)
        MyTextView changeContent;//换一换
        //左边
        @BindView(R.id.home_read_piv_iv1)
        MyImageView homeReadPivIv1;
        @BindView(R.id.home_tv_title1)
        TextView homeTvTitle1;
        @BindView(R.id.home_tv_title2)
        TextView homeTvTitle2;
        @BindView(R.id.tv_video_num)
        TextView tvVideoNum;
        @BindView(R.id.tv_video_time)
        TextView tvVideoTime;
        @BindView(R.id.tv_video_author)
        TextView tvVideoAuthor;
        //右边
        @BindView(R.id.home_read_piv_iv2)
        MyImageView homeReadPivIv2;
        @BindView(R.id.home_tv_right_title1)
        TextView homeTvRightTitle1;
        @BindView(R.id.home_tv_right_title2)
        TextView homeTvRightTitle2;
        @BindView(R.id.tv_right_video_num)
        TextView tvRightVideoNum;
        @BindView(R.id.tv_right_video_time)
        TextView tvRightVideoTime;
        @BindView(R.id.tv_right_video_author)
        TextView tvRightVideoAuthor;

        public TypetypeHolder3(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class TypetypeHolder4 extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_homepageradapter_artist)
        RecyclerView rvtype;
        @BindView(R.id.tv_title)
        MyTextView tvTitle;
        @BindView(R.id.change_content)
        MyTextView changeContent;//换一换


        public TypetypeHolder4(View view) {
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
        if(datas.get(position).getTypeX() == 1){
            return TYPE_BANNER;
        }else if(datas.get(position).getTypeX() == 2){
            return TYPE_NORMAL1;
        }else if(datas.get(position).getTypeX() == 3){
            return TYPE_NORMAL2;
        }else if(datas.get(position).getTypeX() == 4){
            return TYPE_NORMAL3;
        }else if(position == getItemCount() - 1){
            return TYPE_FOOTER;
        }else{
            return 0;
        }
    }
}
