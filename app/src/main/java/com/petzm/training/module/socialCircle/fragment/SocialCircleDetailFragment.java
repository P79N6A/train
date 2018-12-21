package com.petzm.training.module.socialCircle.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.petzm.training.Constant;
import com.petzm.training.R;
import com.petzm.training.base.BaseFragment;
import com.petzm.training.base.MyCallBack;
import com.petzm.training.module.home.bean.HomeObj;
import com.petzm.training.module.home.bean.RefreshObj;
import com.petzm.training.module.socialCircle.adapter.MyRecyclerAdapter;
import com.petzm.training.module.socialCircle.network.ApiRequest;
import com.petzm.training.view.UniversalItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by administartor on 2017/8/2.
 */

public class SocialCircleDetailFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int itemPosition;

    private List<String> imageList;

    //    AllOrderAdapter adapter;
    private String type = "0";
    private ArrayList<RefreshObj> data = new ArrayList<>(); //首页的数据
    private MyRecyclerAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private GridLayoutManager mLayoutManager;
    boolean isTranslucentStatus = false;

    @Override
    protected int getContentView() {
        return R.layout.frag_all_order;
    }

    public static SocialCircleDetailFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putString(Constant.type, type + "");
        SocialCircleDetailFragment fragment = new SocialCircleDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        if(getArguments().getString(Constant.type,"0").equals("4")){
            toolbar.setVisibility(View.VISIBLE);
        }else{
            toolbar.setVisibility(View.GONE);
        }

        adapter = new MyRecyclerAdapter(data, mContext);
        LinearLayoutManager xLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(xLinearLayoutManager);
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
        recyclerView.addItemDecoration(new UniversalItemDecoration() {
            @Override
            public Decoration getItemOffsets(int position) {
                ColorDecoration decoration = new ColorDecoration();
//                if (position == 1) {
//                    decoration.bottom = 20;
//                    decoration.decorationColor = getResources().getColor(R.color.blue);
//                } else if (position > 1) {
//                    decoration.bottom = 20;
//                    decoration.decorationColor = getResources().getColor(R.color.green);
//                } else {
                    decoration.bottom = 20;
                    decoration.decorationColor = getResources().getColor(R.color.gray_f6);
//                }
                return decoration;
            }
        });
//        LinearLayoutManager xLinearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(xLinearLayoutManager);
//        recyclerView.setRefreshProgressStyle(ProgressStyle.BallZigZag); //设定下拉刷新样式
//        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);//设定上拉加载样式
//        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);     //设定下拉刷新显示图片（不必须）
//        recyclerView.setItemAnimator(null);
////        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 5)));
//
//        recyclerView.setAdapter(adapter = new MyRecyclerAdapter(data, mContext));
//        recyclerView.addItemDecoration(new UniversalItemDecoration() {
//            @Override
//            public Decoration getItemOffsets(int position) {
//                ColorDecoration decoration = new ColorDecoration();
//                if(position >= 1){
//                    decoration.bottom = 20;
//                    decoration.decorationColor = getResources().getColor(R.color.gray_f6);
//                }else{
//                    decoration.bottom = 0;
//                    decoration.decorationColor = getResources().getColor(R.color.gray_f6);
//                }
//                return decoration;
//            }
//        });











    }

    @Override
    protected void initData() {
        showProgress();
        getData(true);
    }
//    private void updateRecyclerView(int fromIndex, int toIndex) {
//        List<String> newDatas = getData(fromIndex, toIndex);
//        if (newDatas.size() > 0) {
//            adapter.updateList(newDatas, true);
//        } else {
//            adapter.updateList(null, false);
//        }
//    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
//        RxBus.getInstance().getEvent(GetOrderEvent.class, new MySubscriber() {
//            @Override
//            public void onMyNext(Object o) {
//                showLoading();
//                getData(1,false);
//            }
//        });

    }

    private void getData(boolean refresh) {
        Map<String, String> map = new HashMap<String, String>();

        ApiRequest.getCircleData(map, new MyCallBack<HomeObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(HomeObj obj) {
                if (refresh) {
                    if (obj.getHome().size() > 0) {
                        data.clear();
                        data.addAll(obj.getHome());
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                } else {
                    if (obj.getHome().size() > 0) {
                        data.addAll(obj.getHome());
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishLoadMore();
                    } else {
                        Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                    }
                }
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
//                    getData(1,false);
                    break;
            }
        }
    }

    @Override
    protected void onViewClick(View v) {

    }

}
