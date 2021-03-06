package com.petzm.training.module.course.adapter;

import android.content.Context;

import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.petzm.training.R;
import com.petzm.training.module.course.bean.SearchRecordObj;

public class HistoryAdapter extends BaseRecyclerAdapter<SearchRecordObj.HottestListBean>{


    @Override
    public void bindData(RecyclerViewHolder holder, int i, SearchRecordObj.HottestListBean bean) {

        holder.setText(R.id.tv_history,bean.getSearch_term());
    }

    public HistoryAdapter(Context ctx, int layoutId) {
        super(ctx, layoutId);
    }


}
