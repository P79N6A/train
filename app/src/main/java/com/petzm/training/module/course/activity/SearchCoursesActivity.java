package com.petzm.training.module.course.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.view.MyDialog;
import com.github.customview.FlowLayout;
import com.github.customview.MyTextView;
import com.petzm.training.Constant;
import com.petzm.training.GetSign;
import com.petzm.training.R;
import com.petzm.training.base.BaseActivity;
import com.petzm.training.base.MyCallBack;
import com.petzm.training.module.course.adapter.HistoryAdapter;
import com.petzm.training.module.course.bean.SearchRecordObj;
import com.petzm.training.module.course.network.ApiRequest;
import com.petzm.training.module.my.activity.LoginActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchCoursesActivity extends BaseActivity {
//
//    @BindView(R.id.fl_search_history)
//    FlowLayout fl_search_history;

    private UMShareListener mShareListener;
    private ShareAction mShareAction;

    @BindView(R.id.fl_search_hot)
    FlowLayout fl_search_hot;

    @BindView(R.id.et_search_content)
    EditText et_search_content;

    @BindView(R.id.recyclerview_history)
    RecyclerView recyclerviewHistory;

    HistoryAdapter adapter;


//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
//        mImmersionBar.statusBarColor(R.color.white)
//                .fitsSystemWindows(true)
//                .statusBarDarkFont(true, 0.2f)
//                .init();
//    }

    @Override
    protected int getContentView() {
        return R.layout.act_search_courses;
    }

    @Override
    protected void initView() {

        mShareListener = new CustomShareListener(this);
        /*增加自定义按钮的分享面板*/
        mShareAction = new ShareAction(SearchCoursesActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.ALIPAY, SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN,
                SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL, SHARE_MEDIA.YNOTE,
                SHARE_MEDIA.EVERNOTE, SHARE_MEDIA.LAIWANG, SHARE_MEDIA.LAIWANG_DYNAMIC,
                SHARE_MEDIA.LINKEDIN, SHARE_MEDIA.YIXIN, SHARE_MEDIA.YIXIN_CIRCLE,
                SHARE_MEDIA.TENCENT, SHARE_MEDIA.FACEBOOK, SHARE_MEDIA.TWITTER,
                SHARE_MEDIA.WHATSAPP, SHARE_MEDIA.GOOGLEPLUS, SHARE_MEDIA.LINE,
                SHARE_MEDIA.INSTAGRAM, SHARE_MEDIA.KAKAO, SHARE_MEDIA.PINTEREST,
                SHARE_MEDIA.POCKET, SHARE_MEDIA.TUMBLR, SHARE_MEDIA.FLICKR,
                SHARE_MEDIA.FOURSQUARE, SHARE_MEDIA.MORE)
                .addButton("复制文本", "复制文本", "umeng_socialize_copy", "umeng_socialize_copy")
                .addButton("复制链接", "复制链接", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (snsPlatform.mShowWord.equals("复制文本")) {
                            Toast.makeText(SearchCoursesActivity.this, "复制文本按钮", Toast.LENGTH_LONG).show();
                        } else if (snsPlatform.mShowWord.equals("复制链接")) {
                            Toast.makeText(SearchCoursesActivity.this, "复制链接按钮", Toast.LENGTH_LONG).show();

                        } else {
                            UMWeb web = new UMWeb(Constant.url);
                            web.setTitle("来自分享面板标题");
                            web.setDescription("来自分享面板内容");
                            web.setThumb(new UMImage(SearchCoursesActivity.this, R.drawable.home27));
                            new ShareAction(SearchCoursesActivity.this).withMedia(web)
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        }
                    }
                });
        et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchGoods();
                }
                return false;
            }
        });

        //展示搜索历史列表
        adapter = new HistoryAdapter(mContext, R.layout.item_search_history);
        recyclerviewHistory.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerviewHistory.setNestedScrollingEnabled(false);
        recyclerviewHistory.setAdapter(adapter);

    }


    private static class CustomShareListener implements UMShareListener {

        private WeakReference<SearchCoursesActivity> mActivity;

        private CustomShareListener(SearchCoursesActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), t.getMessage() + " 分享失败啦", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }
    private void searchGoods() {
        if (TextUtils.isEmpty(et_search_content.getText().toString())) {
            showMsg("请输入搜索内容");
        } else {
            PhoneUtils.hiddenKeyBoard(mContext, et_search_content);
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Constant.IParam.searchStr, et_search_content.getText().toString());
//            STActivityForResult(intent, SearchResultActivity.class, 100);
        }
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId() == null ? "0" : getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getSearchRecord(map, new MyCallBack<SearchRecordObj>(mContext, pl_load) {
            @Override
            public void onSuccess(SearchRecordObj obj) {
                fl_search_hot.removeAllViews();
                List<SearchRecordObj.RecentlyListBean> recently_list = new ArrayList<>();
                SearchRecordObj.RecentlyListBean recentlyListBean = new SearchRecordObj.RecentlyListBean();
                recentlyListBean.setSearch_term("哈根达");
                recently_list.add(recentlyListBean);
                adapter.setList(obj.getHottest_list());
                if (notEmpty(obj.getRecently_list())) {
                    adapter.setList(obj.getHottest_list());
//                    for (int i = 0; i < obj.getRecently_list().size(); i++) {
//                        SearchRecordObj.RecentlyListBean bean = obj.getRecently_list().get(i);


//                        MyTextView textView = new MyTextView(mContext);
//                        textView.setText(bean.getSearch_term());
//                        textView.setPadding(PhoneUtils.dip2px(mContext, 12), 0, PhoneUtils.dip2px(mContext, 12), 0);
//                        textView.setGravity(Gravity.CENTER);
//                        textView.setMinHeight(PhoneUtils.dip2px(mContext, 35));
//                        textView.setSolidColor(Color.parseColor("#f5f5f5"));
//                        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(0, 0, PhoneUtils.dip2px(mContext, 10), PhoneUtils.dip2px(mContext, 10));
//                        textView.setLayoutParams(layoutParams);
//                        textView.setRadius(PhoneUtils.dip2px(mContext, 3));
//                        textView.complete();
//                        textView.setOnClickListener(new MyOnClickListener() {
//                            @Override
//                            protected void onNoDoubleClick(View view) {
//                                PhoneUtils.hiddenKeyBoard(mContext, et_search_content);
//                                Intent intent = new Intent();
//                                intent.putExtra(Constant.IParam.searchStr, bean.getSearch_term());
////                                STActivityForResult(intent, SearchResultActivity.class, 100);
//                            }
//                        });
//                        fl_search_history.addView(textView);
//                    }
                }


                if (notEmpty(obj.getHottest_list())) {
                    for (int i = 0; i < obj.getHottest_list().size(); i++) {
                        SearchRecordObj.HottestListBean hottestListBean = obj.getHottest_list().get(i);
                        MyTextView textView = new MyTextView(mContext);
                        textView.setText(hottestListBean.getSearch_term());
                        textView.setPadding(PhoneUtils.dip2px(mContext, 12), 0, PhoneUtils.dip2px(mContext, 12), 0);
                        textView.setGravity(Gravity.CENTER);
                        textView.setMinHeight(PhoneUtils.dip2px(mContext, 35));
                        textView.setSolidColor(Color.parseColor("#f5f5f5"));
                        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, PhoneUtils.dip2px(mContext, 10), PhoneUtils.dip2px(mContext, 10));
                        textView.setLayoutParams(layoutParams);
                        textView.setRadius(PhoneUtils.dip2px(mContext, 3));
                        textView.complete();
                        textView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                PhoneUtils.hiddenKeyBoard(mContext, et_search_content);
                                Intent intent = new Intent();
                                intent.putExtra(Constant.IParam.searchStr, hottestListBean.getSearch_term());
//                                STActivityForResult(intent, SearchResultActivity.class, 100);
                            }
                        });
                        fl_search_hot.addView(textView);
                    }
                }
            }

        });

    }

    @OnClick({R.id.tv_search_cancel, R.id.iv_search_delete})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_delete:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                MyDialog.Builder mDialog = new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认删除历史记录?");
                mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteRecord();
                    }
                });
                mDialog.create().show();
                break;
            case R.id.tv_search_cancel:
//                finish();
//                searchGoods();
                mShareAction.open();
                break;
        }
    }

    private void deleteRecord() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
//        ApiRequest.deleteSearchRecord(map, new MyCallBack<BaseObj>(mContext, true) {
//            @Override
//            public void onSuccess(BaseObj obj) {
//                getData();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==RESULT_OK){
        switch (requestCode) {
            case 100:
                getData();
                break;
        }
        et_search_content.setText(null);
//        }
    }


}
