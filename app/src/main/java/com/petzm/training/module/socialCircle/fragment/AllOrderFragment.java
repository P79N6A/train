//package com.petzmall.training.module.socialCircle.fragment;
//
//import android.app.ActivityOptions;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.github.androidtools.PhoneUtils;
//import com.github.baseclass.adapter.LoadMoreAdapter;
//import com.github.baseclass.rx.MySubscriber;
//import com.github.baseclass.rx.RxBus;
//import com.petzmall.training.Constant;
//import com.petzmall.training.R;
//import com.petzmall.training.base.BaseFragment;
//import com.petzmall.training.base.BaseHelper;
//import com.petzmall.training.base.MyCallBack;
//import com.petzmall.training.constants.P;
//import com.petzmall.training.listener.OnItemPictureClickListener;
//import com.petzmall.training.module.category.bean.CategoryObj;
//import com.petzmall.training.module.home.bean.HomeObj;
//import com.petzmall.training.module.home.bean.RefreshObj;
//import com.petzmall.training.module.socialCircle.activity.ImagePreviewActivity;
//import com.petzmall.training.module.socialCircle.adapter.HomeOtherHuoDongAdapter;
//import com.petzmall.training.module.socialCircle.adapter.MyRecyclerAdapter;
//import com.petzmall.training.module.socialCircle.network.ApiRequest;
//import com.petzmall.training.view.UniversalItemDecoration;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import in.srain.cube.views.ptr.PtrDefaultHandler;
//import in.srain.cube.views.ptr.PtrFrameLayout;
//
//import static android.app.Activity.RESULT_OK;
//
///**
// * Created by administartor on 2017/8/2.
// */
//
//public class AllOrderFragment extends BaseFragment  {
//    @BindView(R.id.rv_all_order)
//    RecyclerView rv_all_order;
//
//    private int itemPosition;
//
//    private List<String> imageList;
//
////    AllOrderAdapter adapter;
//    private String type="0";
////    private HomeOtherHuoDongAdapter homeOtherHuoDongAdapter;//按摩针灸
//    private ArrayList<RefreshObj> homeDatas = new ArrayList<>(); //首页的数据
//    private MyRecyclerAdapter adapter;
//    private Handler mHandler = new Handler(Looper.getMainLooper());
//    private int lastVisibleItem = 0;
//    private final int PAGE_COUNT = 10;
//    private GridLayoutManager mLayoutManager;
//    @Override
//    protected int getContentView() {
//        return R.layout.frag_all_order;
//    }
//
//    public static AllOrderFragment newInstance(int type) {
//        Bundle args = new Bundle();
//        args.putString(Constant.type,type+"");
//        AllOrderFragment fragment = new AllOrderFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//    @Override
//    protected void initView() {
//
//
////        adapter = new MyRecyclerAdapter(homeDatas, mContext, false, new OnItemPictureClickListener() {
////            @Override
////            public void onItemPictureClick(int item, int position, String url, List<String> urlList, ImageView imageView) {
////                itemPosition = item;
////                Intent intent = new Intent(getActivity(), ImagePreviewActivity.class);
////                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
////                intent.putExtra(P.START_ITEM_POSITION, itemPosition);
////                intent.putExtra(P.START_IAMGE_POSITION, position);
////                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
////                startActivity(intent, compat.toBundle());
////            }
////        });
////        rv_all_order.setAdapter(adapter);
////        rv_all_order.setNestedScrollingEnabled(false);
////        mLayoutManager = new GridLayoutManager(mContext, 1);
////        rv_all_order.setLayoutManager(mLayoutManager);
////
////        rv_all_order.addOnScrollListener(new RecyclerView.OnScrollListener() {
////            @Override
////            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
////                super.onScrollStateChanged(recyclerView, newState);
////                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
////                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
////                        mHandler.postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
////                                getData(false);
//////                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
////                            }
////                        }, 0);
////                    }
////
////                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
////                        mHandler.postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
////                                getData(false);
//////                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
////                            }
////                        }, 0);
////                    }
////                }
////            }
////
////            @Override
////            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
////                super.onScrolled(recyclerView, dx, dy);
////                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
////            }
////        });
//        rv_all_order.addItemDecoration(new UniversalItemDecoration() {
//            @Override
//            public Decoration getItemOffsets(int position) {
//                ColorDecoration decoration = new ColorDecoration();
//                decoration.bottom = 20;
//                decoration.decorationColor = getResources().getColor(R.color.gray_f6);
//                return decoration;
//            }
//        });
////        BaseHelper.setLinearLayoutManagerVertical(getActivity(),rv_all_order,homeIndexAdapter);
//        pcfl.setPtrHandler(new PtrDefaultHandler() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//
//                getData(true);
//            }
//        });
//
//    }
//
//    @Override
//    protected void initData() {
//        showProgress();
//        getData(true);
//    }
//
//
////    private void updateRecyclerView(int fromIndex, int toIndex) {
////        List<String> newDatas = getData(fromIndex, toIndex);
////        if (newDatas.size() > 0) {
////            adapter.updateList(newDatas, true);
////        } else {
////            adapter.updateList(null, false);
////        }
////    }
//
//    @Override
//    protected void initRxBus() {
//        super.initRxBus();
////        RxBus.getInstance().getEvent(GetOrderEvent.class, new MySubscriber() {
////            @Override
////            public void onMyNext(Object o) {
////                showLoading();
////                getData(1,false);
////            }
////        });
//
//    }
//
//    private void getData( boolean refresh) {
//        Map<String,String> map=new HashMap<String,String>();
//
//
//          ApiRequest.getCircleData(map, new MyCallBack<HomeObj>(mContext, pcfl, pl_load) {
//            @Override
//            public void onSuccess(HomeObj obj) {
//                if(refresh){
//                    adapter.resetDatas();//如果是下拉刷新，清空数据
//                }
//                if (obj.getHome().size() > 0) {
//                    adapter.updateList((ArrayList<RefreshObj>) obj.getHome(), true);
//                } else {
//                    adapter.updateList(null, false);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==RESULT_OK){
//            switch (requestCode){
//                case 100:
////                    getData(1,false);
//                break;
//            }
//        }
//    }
//
//    @Override
//    protected void onViewClick(View v) {
//
//    }
//
//}
