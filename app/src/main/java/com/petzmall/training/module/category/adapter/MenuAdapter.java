package com.petzmall.training.module.category.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.petzmall.training.R;

import java.util.List;

/**
 * 左侧菜单ListView的适配器
 *
 * @author Administrator
 */
public class MenuAdapter extends BaseAdapter {

    private Context context;
    private int selectItem = 0;
    private List<String> list;

    public MenuAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder holder = null;
        if (arg1 == null) {
            holder = new ViewHolder();
            arg1 = View.inflate(context, R.layout.item_menu, null);

            holder.layout = (FrameLayout) arg1.findViewById(R.id.layout);
            holder.tv_name = (TextView) arg1.findViewById(R.id.item_name);
            holder.iv_line = (ImageView) arg1.findViewById(R.id.iv_line);
            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        if (arg0 == selectItem) {
            holder.layout.setBackgroundColor(Color.WHITE);
            holder.iv_line.setVisibility(View.VISIBLE);
        } else {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.background));
            holder.iv_line.setVisibility(View.GONE);
        }
        holder.tv_name.setText(list.get(arg0));
        return arg1;
    }

    static class ViewHolder {
        private TextView tv_name;
        private ImageView iv_line;
        private FrameLayout  layout;
    }
}
