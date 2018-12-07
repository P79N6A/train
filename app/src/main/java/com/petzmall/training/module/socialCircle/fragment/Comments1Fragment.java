//package com.petzmall.training.module.socialCircle.fragment;
//
//import android.support.design.widget.BottomSheetDialog;
//import android.util.Log;
//import android.view.View;
//import android.widget.ExpandableListView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.petzmall.training.R;
//import com.petzmall.training.base.BaseFragment;
//import com.petzmall.training.base.MyCallBack;
//import com.petzmall.training.module.home.bean.HomeObj;
//import com.petzmall.training.module.socialCircle.adapter.CommentExpandAdapter;
//import com.petzmall.training.module.socialCircle.bean.CommentBean;
//import com.petzmall.training.module.socialCircle.bean.CommentDetailBean;
//import com.petzmall.training.module.socialCircle.network.ApiRequest;
//import com.petzmall.training.view.CommentExpandableListView;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//
//public class Comments1Fragment extends BaseFragment {
//    private static final String TAG = "CommentsFragment";
//    @BindView(R.id.detail_page_lv_comment)
//    CommentExpandableListView expandableListView;
//
//    private CommentExpandAdapter adapter;
//    private CommentBean commentBean;
//    private List<CommentDetailBean> commentsList;
//
//    private BottomSheetDialog dialog;
//    private String testJson = "{\n" +
//            "\t\"code\": 1000,\n" +
//            "\t\"message\": \"查看评论成功\",\n" +
//            "\t\"data\": {\n" +
//            "\t\t\"total\": 3,\n" +
//            "\t\t\"list\": [{\n" +
//            "\t\t\t\t\"id\": 42,\n" +
//            "\t\t\t\t\"nickName\": \"程序猿\",\n" +
//            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
//            "\t\t\t\t\"content\": \"时间是一切财富中最宝贵的财富。\",\n" +
//            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
//            "\t\t\t\t\"replyTotal\": 1,\n" +
//            "\t\t\t\t\"createDate\": \"三分钟前\",\n" +
//            "\t\t\t\t\"replyList\": [{\n" +
//            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
//            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
//            "\t\t\t\t\t\"id\": 40,\n" +
//            "\t\t\t\t\t\"commentId\": \"42\",\n" +
//            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
//            "\t\t\t\t\t\"status\": \"01\",\n" +
//            "\t\t\t\t\t\"createDate\": \"一个小时前\"\n" +
//            "\t\t\t\t}]\n" +
//            "\t\t\t},\n" +
//            "\t\t\t{\n" +
//            "\t\t\t\t\"id\": 41,\n" +
//            "\t\t\t\t\"nickName\": \"设计狗\",\n" +
//            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
//            "\t\t\t\t\"content\": \"这世界要是没有爱情，它在我们心中还会有什么意义！这就如一盏没有亮光的走马灯。\",\n" +
//            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
//            "\t\t\t\t\"replyTotal\": 1,\n" +
//            "\t\t\t\t\"createDate\": \"一天前\",\n" +
//            "\t\t\t\t\"replyList\": [{\n" +
//            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
//            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
//            "\t\t\t\t\t\"commentId\": \"41\",\n" +
//            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
//            "\t\t\t\t\t\"status\": \"01\",\n" +
//            "\t\t\t\t\t\"createDate\": \"三小时前\"\n" +
//            "\t\t\t\t}]\n" +
//            "\t\t\t},\n" +
//            "\t\t\t{\n" +
//            "\t\t\t\t\"id\": 40,\n" +
//            "\t\t\t\t\"nickName\": \"产品喵\",\n" +
//            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
//            "\t\t\t\t\"content\": \"笨蛋自以为聪明，聪明人才知道自己是笨蛋。\",\n" +
//            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
//            "\t\t\t\t\"replyTotal\": 0,\n" +
//            "\t\t\t\t\"createDate\": \"三天前\",\n" +
//            "\t\t\t\t\"replyList\": []\n" +
//            "\t\t\t}\n" +
//            "\t\t]\n" +
//            "\t}\n" +
//            "}";
//
//
//    @Override
//    protected int getContentView() {
//        return R.layout.fragment_comment;
//    }
//
//    @Override
//    protected void initView() {
//        commentsList = generateTestData();
//        initExpandableListView(commentsList);
//    }
//
//    /**
//     * 初始化评论和回复列表
//     */
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
//    @Override
//    protected void initData() {
//        showProgress();
////        getData(true);
//    }
//
//    @Override
//    protected void onViewClick(View v) {
//
//    }
//
////    private void getData(boolean refresh) {
////        Map<String, String> map = new HashMap<String, String>();
////
////        ApiRequest.getCircleData(map, new MyCallBack<HomeObj>(mContext, pcfl, pl_load) {
////            @Override
////            public void onSuccess(HomeObj obj) {
////                if (refresh) {
////                    if (obj.getHome().size() > 0) {
////                        data.clear();
////                        data.addAll(obj.getHome());
////                        adapter.notifyDataSetChanged();
////                        refreshLayout.finishRefresh();
////                        refreshLayout.setNoMoreData(false);
////                    }
////                } else {
////                    if (obj.getHome().size() > 0) {
////                        data.addAll(obj.getHome());
////                        adapter.notifyDataSetChanged();
////                        refreshLayout.finishLoadMore();
////                    } else {
////                        Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
////                        refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
////                    }
////                }
////            }
////        });
////    }
//    /**
//     * by moos on 2018/04/20
//     * func:弹出回复框
//     */
//    private void showReplyDialog(final int position){
////        dialog = new BottomSheetDialog(getActivity());
////        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
////        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
////        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
////        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
////        dialog.setContentView(commentView);
////        bt_comment.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                String replyContent = commentText.getText().toString().trim();
////                if(!TextUtils.isEmpty(replyContent)){
////
////                    dialog.dismiss();
////                    ReplyDetailBean detailBean = new ReplyDetailBean("小红",replyContent);
////                    adapter.addTheReplyData(detailBean, position);
////                    expandableListView.expandGroup(position);
////                    Toast.makeText(MainActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
////                }else {
////                    Toast.makeText(MainActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
////        commentText.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
////                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
////                }else {
////                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
////                }
////            }
////
////            @Override
////            public void afterTextChanged(Editable editable) {
////
////            }
////        });
////        dialog.show();
//    }
//    /**
//     * by moos on 2018/04/20
//     * func:生成测试数据
//     * @return 评论数据
//     */
//    private List<CommentDetailBean> generateTestData(){
//        Gson gson = new Gson();
//        commentBean = gson.fromJson(testJson, CommentBean.class);
//        List<CommentDetailBean> commentList = commentBean.getData().getList();
//        return commentList;
//    }
//}
