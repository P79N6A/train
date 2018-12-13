package com.petzmall.training.module.socialCircle.fragment;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.androidtools.PhoneUtils;
import com.google.gson.Gson;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseFragment;
import com.petzmall.training.base.MyCallBack;
import com.petzmall.training.module.my.activity.LoginPhoneActivity;
import com.petzmall.training.module.socialCircle.adapter.CommentAdapter;
import com.petzmall.training.module.socialCircle.adapter.CommentExpandAdapter;
import com.petzmall.training.module.socialCircle.adapter.MyAdapter;
import com.petzmall.training.module.socialCircle.bean.CommentBean;
import com.petzmall.training.module.socialCircle.bean.CommentDetailBean;
import com.petzmall.training.module.socialCircle.bean.CommentsBean;
import com.petzmall.training.module.socialCircle.bean.SecondCommentBean;
import com.petzmall.training.module.socialCircle.network.ApiRequest;
import com.petzmall.training.view.DividerGridItemDecoration;
import com.petzmall.training.view.UniversalItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CommentsFragment extends BaseFragment {
    private static final String TAG = "CommentsFragment";
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentsList;
    CommentsBean commentsBean;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private BottomSheetDialog dialog;
    private LinearLayoutManager layoutManager;
    CommentAdapter commentAdapter;
    boolean addHeader = false;//是否添加了头部

    @Override
    protected int getContentView() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView() {
        commentAdapter=new CommentAdapter(mContext,0);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 1)));


    }

    /**
     * 初始化评论和回复列表
     */
//    private void initExpandableListView(final List<CommentDetailBean> commentList){
//        expandableListView.setGroupIndicator(null);
//        //默认展开所有回复
//        adapter = new CommentExpandAdapter(getContext(), commentList);
//        expandableListView.setAdapter(adapter);
//        for(int i = 0; i<commentList.size(); i++){
//            expandableListView.expandGroup(i);
//        }
//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
//                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
//                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
////                if(isExpanded){
////                    expandableListView.collapseGroup(groupPosition);
////                }else {
////                    expandableListView.expandGroup(groupPosition, true);
////                }
//                showReplyDialog(groupPosition);
//                return true;
//            }
//        });
//
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
//                Toast.makeText(getActivity(),"点击了回复",Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                //toast("展开第"+groupPosition+"个分组");
//
//            }
//        });
//
//    }
    @Override
    protected void initData() {
        showProgress();
        getData(true);
    }

    @Override
    protected void onViewClick(View v) {

    }

    private void getData(boolean refresh) {
        Map<String, String> map = new HashMap<String, String>();
        ApiRequest.getCommentData(map, new MyCallBack<CommentsBean>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(CommentsBean obj) {
                if (refresh) {
                    if (obj.getData().size() > 0) {
//                        data.clear();
//                        data.addAll(obj.getHome());
//                        adapter.notifyDataSetChanged();
//                        addHeader(dataBean);
                        commentAdapter.setList(obj.getData(),true);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                } else {
                    if (obj.getData().size() > 0) {
//                        data.addAll(obj.getHome());
//                        adapter.notifyDataSetChanged();
                        refreshLayout.finishLoadMore();
                    } else {
                        Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                    }
                }
            }

        });
    }


//    public void addHeader(SecondCommentBean.DataBean dataBean) {
//        if (!addHeader) {
//            //添加轮播图
//            View view = LayoutInflater.from( ).inflate(R.layout.comment_second_title_layout, (ViewGroup) findViewById(android.R.id.content), false);
//            circleImageView = view.findViewById(R.id.comment_item_logo);
//            commentUserName = view.findViewById(R.id.comment_item_userName);
//            commentTime = view.findViewById(R.id.comment_item_time);
//            commentContent = view.findViewById(R.id.comment_item_content);
//            commentUserName.setText(dataBean.getFromUname());
//            commentTime.setText(dataBean.getReplyTime());
//            commentContent.setText(dataBean.getContent());
//            Glide.with(mContext).load(dataBean.getFromUimg())
//                    .into(circleImageView);
//            recyclerView.addHeaderView(view);
//        }
//        addHeader = true;
//    }


    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position){
//        dialog = new BottomSheetDialog(getActivity());
//        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
//        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
//        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
//        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
//        dialog.setContentView(commentView);
//        bt_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String replyContent = commentText.getText().toString().trim();
//                if(!TextUtils.isEmpty(replyContent)){
//
//                    dialog.dismiss();
//                    ReplyDetailBean detailBean = new ReplyDetailBean("小红",replyContent);
//                    adapter.addTheReplyData(detailBean, position);
//                    expandableListView.expandGroup(position);
//                    Toast.makeText(MainActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(MainActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        commentText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
//                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
//                }else {
//                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        dialog.show();
    }

}
