package com.petzmall.training.module.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseFragment;
import com.petzmall.training.base.MyCallBack;
import com.petzmall.training.module.home.adapter.HomeRecyclerAdapter;
import com.petzmall.training.module.home.bean.HomeObj;
import com.petzmall.training.module.home.bean.RefreshObj;
import com.petzmall.training.module.home.event.RefreshEvent;
import com.petzmall.training.module.home.network.ApiRequest;
import com.petzmall.training.module.player.activity.AliyunPlayerSkinActivity;
import com.petzmall.training.view.ProgressLayout;
import com.petzmall.training.view.UniversalItemDecoration;
import com.petzmall.training.view.WrapContentLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.et_search_content)
    TextView etSearchContent;
    @BindView(R.id.pl_load)
    ProgressLayout plLoad;
    private List<String> bannerList;

    private ArrayList<RefreshObj> data = new ArrayList<>(); //首页的数据

    private HomeRecyclerAdapter adapter;
    boolean refresh;
    @Override
    protected int getContentView() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        adapter = new HomeRecyclerAdapter(data, mContext, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
                 if (position >= 1) {
                    decoration.bottom = 20;
                    decoration.decorationColor = getResources().getColor(R.color.gray_f6);
                } else {
                    decoration.bottom = 1;
                    decoration.decorationColor = getResources().getColor(R.color.gray_f6);
                }
                return decoration;
            }
        });

    }


    @Override
    protected void initRxBus() {
        super.initRxBus();
        RxBus.getInstance().getEvent(RefreshEvent.class, new MySubscriber<RefreshEvent>() {
            @Override
            public void onMyNext(RefreshEvent event) {
//                homepagerRecycleAdapter.notifyItemChanged(2);
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData(true);
    }

    private void getData(boolean refresh) {
        ApiRequest.getHomeData(new MyCallBack<HomeObj>(mContext, pcfl, pl_load) {
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
//                    adapter.updateList((ArrayList<RefreshObj>) obj.getHome(), true);
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (bn_home != null && bannerList != null) {
//            bn_home.stopAutoPlay();
//        }
    }

    @Override
    public void onResume() {


        super.onResume();
//        if (bn_home != null && bannerList != null) {
//            bn_home.startAutoPlay();
//        }
    }


    @OnClick({R.id.toolbar})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                STActivity(AliyunPlayerSkinActivity.class);
                break;

        }
    }
}
