package com.petzmall.training.module.my.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.petzmall.training.Config;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseFragment;



import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by administartor on 2017/8/4.
 */

public class MyFragment extends BaseFragment {


    @BindView(R.id.civ_my_img)
    CircleImageView civ_my_img;
    @BindView(R.id.tv_my_name)
    TextView tv_my_name;


    @BindView(R.id.app_title)
    TextView appTitle;
//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
//        ImmersionBar.with(this)
//                .fitsSystemWindows(true)
//                .statusBarDarkFont(true, 0.2f)
//                .statusBarColor(R.color.white)
//                .init();
//    }
    @Override
    protected int getContentView() {
        return R.layout.frag_my;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
//        if(TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.user_id,null))){
//            return;
//        }
//        getData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if(!hidden){
//            if(TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.user_id,null))){
//                return;
//            }
//            getData();
//        }
    }

//    private void getData() {
////        if(TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.user_id,null))){
////            return;
////        }
//        Map<String,String> map=new HashMap<String,String>();
//        map.put("user_id",getUserId());
//        map.put("sign", GetSign.getSign(map));
//        ApiRequest.getUserInfo(map, new MyCallBack<UserInfoObj>(mContext) {
//            @Override
//            public void onSuccess(UserInfoObj obj) {
//                SPUtils.setPrefString(mContext, Config.mobile,obj.getMobile());
//                SPUtils.setPrefString(mContext, Config.sex,obj.getSex());
//                SPUtils.setPrefString(mContext, Config.avatar,obj.getAvatar());
//                SPUtils.setPrefString(mContext, Config.birthday,obj.getBirthday());
//                SPUtils.setPrefString(mContext, Config.nick_name,obj.getNick_name());
//                SPUtils.setPrefString(mContext, Config.user_name,obj.getUser_name());
//                SPUtils.setPrefString(mContext, Config.amount,obj.getAmount()+"");
//                SPUtils.setPrefInt(mContext, Config.count_wsy,obj.getCount_wsy());
//                SPUtils.setPrefInt(mContext, Config.keeping_bean,obj.getKeeping_bean());
//                SPUtils.setPrefInt(mContext, Config.news_is_check,obj.getNews_is_check());
//                getInfo();
//            }
//        });
//    }

    private void getInfo() {
        String nick_name = SPUtils.getPrefString(mContext, Config.nick_name, null);
        String avatar = SPUtils.getPrefString(mContext, Config.avatar, null);
        int count_wsy = SPUtils.getPrefInt(mContext, Config.count_wsy, 0);


        if (avatar != null) {
            Glide.with(mContext).load(avatar).into(civ_my_img);
        }

    }

    @Override
    protected void initView() {
        appTitle.setText("我的");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }


}
