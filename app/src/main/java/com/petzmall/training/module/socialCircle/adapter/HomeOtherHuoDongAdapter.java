package com.petzmall.training.module.socialCircle.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.petzmall.training.R;
import com.petzmall.training.listener.OnItemPictureClickListener;
import com.petzmall.training.module.home.bean.RefreshObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administartor on 2017/9/12.
 */

public class HomeOtherHuoDongAdapter extends BaseRecyclerAdapter<RefreshObj> {

    private OnItemPictureClickListener listener;

    public HomeOtherHuoDongAdapter(Context mContext, int layoutId ,OnItemPictureClickListener listener) {
        super(mContext, layoutId);

        this.listener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_circle1_moment, parent, false));
        return holder;

    }
    @Override
    public void bindData(RecyclerViewHolder holder, int i, RefreshObj bean) {
//        NineGridTestLayout nineGridTestLayout = (NineGridTestLayout) holder.getView(R.id.nineTestlayout);
//        nineGridTestLayout.setListener(listener);
//        nineGridTestLayout.setItemPosition(i);
//        nineGridTestLayout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
////        nineGridTestLayout.setSpacing(5); //动态设置图片之间的间隔
//        List<String> list = new ArrayList<>();
//        for(int j = 0;j<bean.getImgVo().size();j++){
//            list.add(bean.getImgVo().get(j).getIconUrl());
//        }
////        for(RefreshObj.ImgVoBean s : bean.getImgVo()){
////            list.add(s.getIconUrl());
////        }
//        nineGridTestLayout.setUrlList(list);
    }
    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }
}
