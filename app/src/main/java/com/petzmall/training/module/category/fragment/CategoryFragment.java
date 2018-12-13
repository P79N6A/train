package com.petzmall.training.module.category.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.petzmall.training.Constant;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseFragment;
import com.petzmall.training.base.MyCallBack;
import com.petzmall.training.module.category.activity.CategoryListActivity;
import com.petzmall.training.module.category.adapter.HomeAdapter;
import com.petzmall.training.module.category.adapter.MenuAdapter;
import com.petzmall.training.module.category.bean.CategoryBean;
import com.petzmall.training.module.category.bean.CategoryObj;
import com.petzmall.training.module.category.network.ApiRequest;
import com.petzmall.training.module.my.activity.LoginPhoneActivity;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/31.
 */

public class CategoryFragment extends BaseFragment  {


//    @BindView(R.id.recyclerview_category)
//    RecyclerView recyclerviewCategory;
//    @BindView(R.id.recyclerview_wares)
//    RecyclerView recyclerviewWares;
    @BindView(R.id.lv_menu)
    ListView lvMenu;
    @BindView(R.id.lv_home)
    ListView lvHome;

    private List<String> menuList = new ArrayList<>();
    private List<CategoryObj.CategoriesBean> homeList = new ArrayList<>();
    private List<Integer> showTitle;


    private MenuAdapter menuAdapter;
    private HomeAdapter homeAdapter;
    private int currentItem;


    @BindView(R.id.app_title)
    TextView appTitle;

    @Override
    protected int getContentView() {
        return R.layout.frag_category;
    }

    @Override
    protected void initView() {

        appTitle.setText("分类");

        menuAdapter = new MenuAdapter(getActivity(), menuList);
        lvMenu.setAdapter(menuAdapter);

        homeAdapter = new HomeAdapter(getActivity(), homeList);
        lvHome.setAdapter(homeAdapter);

        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
//                tv_title.setText(menuList.get(position));
                lvHome.setSelection(showTitle.get(position));
            }
        });


        lvHome.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = showTitle.indexOf(firstVisibleItem);
//				lv_home.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
//                    tv_title.setText(menuList.get(currentItem));
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData();

    }

    @Override
    protected void onViewClick(View v) {

    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();

        ApiRequest.getGoodsCategoryList(map, new MyCallBack<CategoryObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(CategoryObj obj) {

                showTitle = new ArrayList<>();
                for (int i = 0; i < obj.getCategories().size(); i++) {
                    menuList.add(obj.getCategories().get(i).getName());
                    showTitle.add(i);
                    homeList.add(obj.getCategories().get(i));
                }
//                tv_title.setText(categoryBean.getData().get(0).getModuleTitle());

                menuAdapter.notifyDataSetChanged();
                homeAdapter.notifyDataSetChanged();


            }


        });
    }

}
