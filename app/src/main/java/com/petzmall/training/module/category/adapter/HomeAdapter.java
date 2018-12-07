package com.petzmall.training.module.category.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.customview.MyImageView;
import com.petzmall.training.R;
import com.petzmall.training.module.category.bean.CategoryObj;
import com.petzmall.training.view.MyGridView;

import java.util.List;

/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryObj.CategoriesBean> foodDatas;

    public HomeAdapter(Context context, List<CategoryObj.CategoriesBean> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }

    @Override
    public int getCount() {
        if (foodDatas != null) {
            return foodDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryObj.CategoriesBean dataBean = foodDatas.get(position);
        List<CategoryObj.CategoriesBean.SubcategoriesBean> dataList = dataBean.getSubcategories();
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home, null);
            viewHold = new ViewHold();
            viewHold.gridView =  convertView.findViewById(R.id.gridView);
            viewHold.imageView =  convertView.findViewById(R.id.iv_title);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        HomeItemAdapter adapter = new HomeItemAdapter(context, dataList);
        Glide.with(context).load(dataBean.getIcon_url()).into(viewHold.imageView);
        viewHold.gridView.setAdapter(adapter);
        return convertView;
    }

    private static class ViewHold {
        private MyGridView gridView;
        private MyImageView imageView;
    }

}
