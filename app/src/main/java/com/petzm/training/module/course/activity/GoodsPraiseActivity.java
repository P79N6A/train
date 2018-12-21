//package com.petzmall.training.module.course.activity;
//
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.github.androidtools.PhoneUtils;
//import com.petzmall.training.R;
//import com.petzmall.training.base.BaseActivity;
//import com.petzmall.training.module.course.adapter.GoodPraiseAdatpter;
//
//import java.util.ArrayList;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class GoodsPraiseActivity extends BaseActivity {
//    @BindView(R.id.rv_free_course_list)
//    RecyclerView rvFreeCourseList;
//
//    @BindView(R.id.rv_good_course_list)
//    RecyclerView rvGoodCourseList;
//    private GoodPraiseAdatpter goodPraiseAdatpter;//商铺分类入口
//    private GoodPraiseAdatpter good1PraiseAdatpter;//商铺分类入口
//    private  ArrayList bannerList;
//
//    @Override
//    protected int getContentView() {
//
//        setAppTitle("好评好课");
//        setAppRightImg(R.drawable.search);
//        return R.layout.act_goods_praise;
//    }
//
//    @Override
//    protected void initView() {
//
//        goodPraiseAdatpter = new GoodPraiseAdatpter(mContext, 0);
//        rvFreeCourseList.setAdapter(goodPraiseAdatpter);
//        rvFreeCourseList.setNestedScrollingEnabled(false);
//        rvFreeCourseList.setLayoutManager(new GridLayoutManager(mContext, 2));
//
//        good1PraiseAdatpter = new GoodPraiseAdatpter(mContext, 0);
//        rvGoodCourseList.setAdapter(good1PraiseAdatpter);
//        rvGoodCourseList.setNestedScrollingEnabled(false);
//        rvGoodCourseList.setLayoutManager(new GridLayoutManager(mContext, 2));
//
//    }
//
//    @Override
//    protected void initData() {
//        bannerList = new ArrayList<>(4);
//        bannerList.add(banner1);
//        bannerList.add(banner2);
//        goodPraiseAdatpter.setList(bannerList);
//        good1PraiseAdatpter.setList(bannerList);
//    }
//
//    @Override
//    protected void onViewClick(View v) {
//
//    }
//
//}
