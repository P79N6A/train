package com.petzm.training.module.socialCircle.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.customview.FlowLayout;
import com.github.customview.MyTextView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.petzm.training.R;
import com.petzm.training.base.BaseFragment;
import com.petzm.training.base.MyCallBack;
import com.petzm.training.module.socialCircle.adapter.RecyclerviewVideoAdapter;
import com.petzm.training.module.socialCircle.bean.Video;
import com.petzm.training.module.socialCircle.network.ApiRequest;
import com.petzm.training.view.ProgressLayout;
import com.petzm.training.view.UniversalItemDecoration;
import com.petzm.training.view.WrapContentLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class VideoIntroduceFragment extends BaseFragment {
    public static final String ARGS_PAGE = "args_page";
    @BindView(R.id.recyclerview)
    XRecyclerView recyclerView;
    @BindView(R.id.pl_load)
    ProgressLayout plLoad;
//    @BindView(R.id.fl_label)
//    FlowLayout fl_label;
    RecyclerviewVideoAdapter adapter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    boolean addHeader= false;//是否添加了头部
    private List<Video.VideolistBean> videolist = new ArrayList<>();
    public static VideoIntroduceFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        VideoIntroduceFragment fragment = new VideoIntroduceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_video_introduce;
    }

    @Override
    protected void initView() {
        adapter=new RecyclerviewVideoAdapter(mContext,0);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new UniversalItemDecoration() {
            @Override
            public Decoration getItemOffsets(int position) {
                ColorDecoration decoration = new ColorDecoration();
                if (position > 1) {
                decoration.bottom = 1;
                decoration.decorationColor = getResources().getColor(R.color.c_divider);
                }
                return decoration;
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getData(false);  //上拉加载添加数据
            }
        });
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void initData() {

        getData(true);

//        for (int i = 0; i < mDataList.size(); i++) {
//            MyTextView textView = new MyTextView(mContext);
//            textView.setText(mDataList.get(i));
//            textView.setPadding(PhoneUtils.dip2px(mContext, 8), 5, PhoneUtils.dip2px(mContext, 8), 5);
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextSize(10);
//            textView.setIncludeFontPadding(false);
//            textView.setTextColor(getResources().getColor(R.color.video_flowlayout_color));
//            textView.setMinHeight(PhoneUtils.dip2px(mContext, 20));
//            textView.setSolidColor(Color.parseColor("#EEF5FE"));
//            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(0, 0, PhoneUtils.dip2px(mContext, 13), PhoneUtils.dip2px(mContext, 0));
//            textView.setLayoutParams(layoutParams);
//            textView.setRadius(PhoneUtils.dip2px(mContext, 15));
//            textView.complete();
//            textView.setOnClickListener(new MyOnClickListener() {
//                @Override
//                protected void onNoDoubleClick(View view) {
////                    Intent intent = new Intent();
////                    intent.putExtra(Constant.IParam.searchStr, hottestListBean.getSearch_term());
////                                STActivityForResult(intent, SearchResultActivity.class, 100);
//                }
//            });
//            fl_label.addView(textView);
//        }
    }


    private void getData( boolean refresh) {
        Map<String, String> map = new HashMap<String, String>();
        ApiRequest.getVideoData(map, new MyCallBack<Video>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(Video obj) {

                if (refresh) {
                    if (obj.getVideolist().size() > 0) {
                        addHeader(obj.getIntroduce());
                        videolist.clear();
                        videolist.addAll(obj.getVideolist());
                        adapter.setList(obj.getVideolist(),true);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                } else {
                    if (obj.getVideolist().size() > 0) {
                        videolist.addAll(obj.getVideolist());
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishLoadMore();
                    } else {
                        Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件

                    }
//                    adapter.updateList((ArrayList<RefreshObj>) obj.getHome(), true);
                }
//                if (obj.getVideolist().size() > 0) {
//                    if(refresh){
//                        addHeader(obj.getIntroduce());
//                        videolist.clear();
//                        videolist.addAll(obj.getVideolist());
//                        adapter.setList(obj.getVideolist(),true);
//                    }else {
//                        adapter.addList(obj.getVideolist(),true);
//                        adapter.notifyDataSetChanged();
//                    }
//                } else {
//
//                }
            }



        });
    }

     public  void addHeader(Video.IntroduceBean introduceBean){
        if(!addHeader){
            //添加轮播图
            View view =   LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video_introduce_header, (ViewGroup)findActivityViewById(android.R.id.content),false);
            FlowLayout flowLayout =  view.findViewById(R.id.fl_label);

            for (int i = 0; i < introduceBean.getLabel().size(); i++) {
                MyTextView textView = new MyTextView(mContext);
                textView.setText(introduceBean.getLabel().get(i).getName());
                textView.setPadding(PhoneUtils.dip2px(mContext, 8), 5, PhoneUtils.dip2px(mContext, 8), 5);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(10);
                textView.setIncludeFontPadding(false);
                textView.setTextColor(getResources().getColor(R.color.video_flowlayout_color));
                textView.setMinHeight(PhoneUtils.dip2px(mContext, 20));
                textView.setSolidColor(Color.parseColor("#EEF5FE"));
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, PhoneUtils.dip2px(mContext, 13), PhoneUtils.dip2px(mContext, 0));
                textView.setLayoutParams(layoutParams);
                textView.setRadius(PhoneUtils.dip2px(mContext, 15));
                textView.complete();
                textView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
//                    Intent intent = new Intent();
//                    intent.putExtra(Constant.IParam.searchStr, hottestListBean.getSearch_term());
//                                STActivityForResult(intent, SearchResultActivity.class, 100);
                    }
                });
                flowLayout.addView(textView);
            }
            recyclerView.addHeaderView(view);
        }
         addHeader =true;
     }


    @Override
    protected void onViewClick(View v) {

    }
}
