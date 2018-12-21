package com.petzm.training.module.socialCircle.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.petzm.training.R;
import com.petzm.training.base.BaseFragment;
import com.petzm.training.module.socialCircle.activity.VideoPlayActivity;
import com.petzm.training.module.socialCircle.adapter.MyPagerAdapter;
import com.petzm.training.view.MyViewPager;

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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SocialCircleFragment extends BaseFragment {

    @BindView(R.id.vp_my_order)
    MyViewPager mViewPager;
    @BindView(R.id.magic_indicator3)
    MagicIndicator magicIndicator;

    private static final String[] CHANNELS = new String[]{"推荐", "附近", "最新", "关注", "话题"};
    @BindView(R.id.app_right_iv)
    ImageView appRightIv;
    private List<String> mDataList = Arrays.asList(CHANNELS);
    //    List<Fragment> fragments = new ArrayList<>();
    MyPagerAdapter adapter;

    private int type;
    List<Fragment> list;
    @BindView(R.id.app_title)
    TextView appTitle;

    @Override
    protected int getContentView() {

        return R.layout.frag_circle;
    }


    @Override
    protected void initView() {
        appTitle.setText("圈子");
        appRightIv.setImageResource(R.drawable.add);
        appRightIv.setVisibility(View.VISIBLE);

    }


    @Override
    protected void initData() {
        list = new ArrayList<>();
        //设置TabLayout点击事件
        for (int i = 0; i < 5; i++) {
            list.add(SocialCircleDetailFragment.newInstance(i));
        }
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
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
        adapter = new MyPagerAdapter(getChildFragmentManager(), mDataList, list);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
    }

    @OnClick({ R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.app_right_iv:
                STActivity(VideoPlayActivity.class);
                break;

        }
    }

}
