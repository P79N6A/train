//package com.petzmall.training.module.socialCircle.activity;
//
//import android.content.res.ColorStateList;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.util.TypedValue;
//import android.view.View;
//import android.widget.LinearLayout;
//
//import com.petzmall.training.R;
//import com.petzmall.training.base.BaseActivity;
//import com.petzmall.training.module.socialCircle.adapter.MyPagerAdapter;
//import com.petzmall.training.module.socialCircle.fragment.VideoIntroduceFragment;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import butterknife.BindView;
//
//public class VideoActivity extends BaseActivity  implements TabLayout.OnTabSelectedListener{
//
//
//
//    @BindView(R.id.view_pager)
//    ViewPager viewPager;
//
//    @BindView(R.id.tl_tab)
//    TabLayout tabLayout;
//    private static final String[] CHANNELS = new String[]{"简介", "大纲", "讨论"};
//    private List<String> mDataList = Arrays.asList(CHANNELS);
//
//    private List<Fragment> fragments = new ArrayList<>();
//    private MyPagerAdapter adapter;
//
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_video;
//    }
//
//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
//        mImmersionBar.statusBarColor(R.color.white)
//                .fitsSystemWindows(true)
//                .statusBarDarkFont(true, 0.2f)
//                .init();
//    }
//
//    @Override
//    protected void initView() {
//        for (String tab : mDataList) {
//            tabLayout.addTab(tabLayout.newTab().setText(tab));
//        }
//        //设置TabLayout点击事件
//        for (int i = 0; i < 3; i++) {
//            fragments.add(VideoIntroduceFragment.newInstance(i));
//        }
//        tabLayout.addOnTabSelectedListener(this);
//        tabLayout.setTabRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//        adapter = new MyPagerAdapter(getSupportFragmentManager(), mDataList, fragments);
//        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(3);
//
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                setIndicator(tabLayout, 28, 28);
//            }
//        });
//    }
//
//
//    @Override
//    protected void initData() {
//    }
//    @Override
//    protected void onViewClick(View v) {
//
//    }
//
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//
//    }
//
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab) {
//
//    }
//
//    @Override
//    public void onTabReselected(TabLayout.Tab tab) {
//
//    }
//}
