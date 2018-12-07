package com.petzmall.training.module.socialCircle.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.MySubscriber;
import com.github.customview.FlowLayout;
import com.github.customview.MyImageView;
import com.github.customview.MyTextView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.petzmall.training.PopWindowUtil;
import com.petzmall.training.PopupWindow.CommonPopupWindow;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseActivity;
import com.petzmall.training.base.MyCallBack;
import com.petzmall.training.module.socialCircle.adapter.CommentAdapter;
import com.petzmall.training.module.socialCircle.adapter.CommentSecondAdapter;
import com.petzmall.training.module.socialCircle.adapter.MyAdapter;
import com.petzmall.training.module.socialCircle.adapter.MyPagerAdapter;
import com.petzmall.training.module.socialCircle.bean.CommentDetailBean;
import com.petzmall.training.module.socialCircle.bean.SecondCommentBean;
import com.petzmall.training.module.socialCircle.bean.Video;
import com.petzmall.training.module.socialCircle.event.PopwindowEvent;
import com.petzmall.training.module.socialCircle.fragment.CommentsFragment;
import com.petzmall.training.module.socialCircle.fragment.VideoIntroduceFragment;
import com.petzmall.training.module.socialCircle.network.ApiRequest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class VideotestActivity extends BaseActivity {


    @BindView(R.id.iv_video_preview)
    ImageView mIvVideoPreview;
    @BindView(R.id.tv_player)
    TextView mTvPlayer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    private static final String[] CHANNELS = new String[]{"简介", "大纲", "讨论"};
    @BindView(R.id.comment_linear)
    LinearLayout commentLinear;
    //    @BindView(R.id.button)
//    Button button;
//    @BindView(R.id.v_bottom_line)
//    View vBottomLine;
    private List<String> mDataList = Arrays.asList(CHANNELS);

    private List<Fragment> fragments = new ArrayList<>();
    private MyPagerAdapter adapter;
    private CollapsingToolbarLayoutState state;


    VideoIntroduceFragment videoIntroduceFragment;
    CommentsFragment commentsFragment;

    CommonPopupWindow popupWindow;
    SmartRefreshLayout refreshLayout;
    CommentSecondAdapter commentSecondAdapter;
    private  List<SecondCommentBean.DataBean.FirstVideoReplyVosBean> firstVideoReplyVos = new ArrayList<>();
    SecondCommentBean.DataBean dataBean;
    //popwindow 里面的一级评论人
    CircleImageView circleImageView;
    TextView commentUserName, commentTime, commentContent;
    boolean addHeader= false;//是否添加了头部
    XRecyclerView recyclerView;

    int i0,i1;
    View appBarChildAt;
    AppBarLayout.LayoutParams appBarParams;
    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(PopwindowEvent.class, new MySubscriber<PopwindowEvent>() {
            @Override
            public void onMyNext(PopwindowEvent event) {
                showPopListView();
                Log.e("initRxBus", "initRxBus------>0");
            }
        });
    }

    @Override
    protected void initView() {
        initAppBar();
        commentSecondAdapter=new CommentSecondAdapter(mContext,0);
        //设置图片
        Glide.with(mContext)
                .load("https://p.pstatp.com/weili/bl/55311344760656633.jpg")
                .into(mIvVideoPreview);

        //设置TabLayout点击事件
        fragments.add(new VideoIntroduceFragment());
        fragments.add(new VideoIntroduceFragment());
        fragments.add(new CommentsFragment());
        initMagicIndicator();

        //控制AppBarLayout折叠
         i0 = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
         i1 = AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
         appBarChildAt = mAppBar.getChildAt(0);
         appBarParams = (AppBarLayout.LayoutParams) appBarChildAt.getLayoutParams();


    }

    private void initAppBar() {
        mAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                    mTvPlayer.setVisibility(View.GONE);
                    mToolbar.setVisibility(View.GONE);
                    mImmersionBar.statusBarColor(R.color.white)
                            .statusBarDarkFont(true, 0.2f)
                            .init();
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    mTvPlayer.setVisibility(View.VISIBLE);
                    mToolbar.setVisibility(View.VISIBLE);
                    mImmersionBar.statusBarColor(R.color.video_toolbar_background)
                            .statusBarDarkFont(true, 0.2f)
                            .init();
                }
            } else {
                mTvPlayer.setVisibility(View.GONE);
                mToolbar.setVisibility(View.GONE);
                mImmersionBar.statusBarColor(R.color.white)
                        .statusBarDarkFont(true, 0.2f)
                        .init();
            }
        });
    }


    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.circle_tablayout_unselect_color));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.circle_tablayout_select_color));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                        if (index == 2) {
                            commentLinear.setVisibility(View.VISIBLE);
                        }else{
                            commentLinear.setVisibility(View.GONE);
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.circle_tablayout_select_color));
                return linePagerIndicator;
            }
        });

        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        adapter = new MyPagerAdapter(getSupportFragmentManager(), mDataList, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 2) {
                    commentLinear.setVisibility(View.VISIBLE);
                }else{
                    commentLinear.setVisibility(View.GONE);
                }
                // arg0是当前选中的页面的Position
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // arg0 :当前页面，及你点击滑动的页面；arg1:当前页面偏移的百分比；arg2:当前页面偏移的像素位置
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                //arg0 ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没做。
//                if(arg0 == 0){
//                    Log.e(TAG, "onPageScrollStateChanged------>0");
//                }else if(arg0 == 1){
//                    Log.e(TAG, "onPageScrollStateChanged------>1");
//                }else if(arg0 == 2){
//                    Log.e(TAG, "onPageScrollStateChanged------>2");
//                }

            }
        });


    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.tv_player})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                appBarParams.setScrollFlags(i0 | i1);//重置折叠效果
                appBarChildAt.setLayoutParams(appBarParams);
                break;
            case R.id.tv_player:
                appBarParams.setScrollFlags(0);//这个加了之后不可滑动
                appBarChildAt.setLayoutParams(appBarParams);
                break;
        }
    }


    private void showPopListView() {
         View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list, null);
         handleListView(contentView);
         popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(contentView)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.popupAnimation)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
//                        TextView tv_child = (TextView) view.findViewById(R.id.tv_child);
//                        tv_child.setText("我是子View");
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(false)
                //开始构建
                .create();
            //弹出PopupWindow
        appBarParams.setScrollFlags(0);//这个加了之后不可滑动
        appBarChildAt.setLayoutParams(appBarParams);
        popupWindow.showAsDropDown(mIvVideoPreview);
    }

    private void handleListView(View contentView) {

        //关闭popwindow
        MyImageView  cancelPop = contentView.findViewById(R.id.cancel);
        cancelPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarParams.setScrollFlags(i0 | i1);//重置折叠效果
                appBarChildAt.setLayoutParams(appBarParams);
                popupWindow.dismiss();
                addHeader = false;
            }
        });

        recyclerView =  contentView.findViewById(R.id.recyclerview);
        refreshLayout = contentView.findViewById(R.id.refreshLayout);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

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
        recyclerView.setAdapter(commentSecondAdapter);
        getData(true);
    }

    private void getData( boolean refresh) {
        Map<String, String> map = new HashMap<String, String>();

        ApiRequest.getSecondComments(map, new MyCallBack<SecondCommentBean>(mContext) {
            @Override
            public void onSuccess(SecondCommentBean obj) {
                if (refresh) {
                    if (obj.getData().getFirstVideoReplyVos().size() > 0) {
                        dataBean = obj.getData();
                        addHeader(dataBean);
                        firstVideoReplyVos.addAll(obj.getData().getFirstVideoReplyVos());
                        commentSecondAdapter.setList(obj.getData().getFirstVideoReplyVos(),true);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                } else {
                    if (obj.getData().getFirstVideoReplyVos().size() > 0) {
                        firstVideoReplyVos.addAll(obj.getData().getFirstVideoReplyVos());
                        commentSecondAdapter.notifyDataSetChanged();
                        refreshLayout.finishLoadMore();
                    } else {
                        Toast.makeText(getContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                    }
                }
            }
        });
    }

    public  void addHeader(SecondCommentBean.DataBean dataBean){
        if(!addHeader){
            //添加轮播图
            View view =   LayoutInflater.from(this).inflate(R.layout.comment_second_title_layout, (ViewGroup)findViewById(android.R.id.content),false);
            circleImageView =  view.findViewById(R.id.comment_item_logo);
            commentUserName = view.findViewById(R.id.comment_item_userName);
            commentTime = view.findViewById(R.id.comment_item_time);
            commentContent = view.findViewById(R.id.comment_item_content);
            commentUserName.setText(dataBean.getFromUname());
            commentTime.setText(dataBean.getReplyTime());
            commentContent.setText(dataBean.getContent());
            Glide.with(mContext).load(dataBean.getFromUimg())
                    .into(circleImageView);
            recyclerView.addHeaderView(view);
        }
        addHeader =true;
    }

    @Override
    public void onBackPressed() {
        if(popupWindow!= null && popupWindow.isShowing()){
            appBarParams.setScrollFlags(i0 | i1);//重置折叠效果
            appBarChildAt.setLayoutParams(appBarParams);
            popupWindow.dismiss();
            addHeader = false;
        }else{
            super.onBackPressed();
        }
    }
}
