package com.petzmall.training.network;

import com.petzmall.training.base.BaseObj;
import com.petzmall.training.base.ResponseObj;
import com.petzmall.training.module.category.bean.CategoryObj;
import com.petzmall.training.network.response.AddGoods;
import com.petzmall.training.network.response.AddGoodsItem;
import com.petzmall.training.network.response.AddGoodsObj;
import com.petzmall.training.network.response.BillObj;
import com.petzmall.training.network.response.GradObj;
import com.petzmall.training.network.response.HomeBannerObj;
import com.petzmall.training.network.response.LoginObj;
import com.petzmall.training.network.response.MoneyDetailObj;
import com.petzmall.training.network.response.OrderDetailObj;
import com.petzmall.training.network.response.OrderListObj;
import com.petzmall.training.network.response.RegisterObj;
import com.petzmall.training.network.response.UploadImgItem;
import com.petzmall.training.network.response.WalletObj;
import com.petzmall.training.network.response.WithdrawalsObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {

    //首页轮播
    @GET("api/HomePage/GetHomePageTop")
    Call<ResponseObj<HomeBannerObj>> getHomePage(@QueryMap Map<String,String> map);

    //注册
    @POST("api/Login/GetUserRegister")
    Call<ResponseObj<RegisterObj>> userRegister(@QueryMap Map<String,String> map);

    //获取商品页面的下拉列表
    @POST("api/App/GetSlecte")
    Call<ResponseObj<AddGoodsObj>> getSpinnerData(@QueryMap Map<String,String> map);

    //登陆
    @POST("console/outer/loginToken")
    Call<ResponseObj<LoginObj>> userLogin(@QueryMap Map<String,String> map);

    //修改密码
    @GET("api/Login/RetrievePwd")
    Call<ResponseObj<Integer>> userRetrievePwd(@QueryMap Map<String,String> map);

    //新增商品
    @POST("api/App/AddGoods")
    Call<ResponseObj<AddGoods>> addGoods(@QueryMap Map<String,String> map, @Body AddGoodsItem item);

    //编辑商品
    @POST("api/App/UpdateGoods")
    Call<ResponseObj<AddGoods>> editGoods(@QueryMap Map<String,String> map, @Body AddGoodsItem item);

    //获取商品详情
    @POST("api/App/GetGoods")
    Call<ResponseObj<AddGoodsItem>> getGoodsDetail(@QueryMap Map<String,String> map);

    //获取商品列表
    @POST("api/App/GetGoodsList")
    Call<ResponseObj<List<OrderDetailObj>>> getGoodsList(@QueryMap Map<String,String> map);

    //获取商品信息
    @POST("api/App/GetOrderListGoods")
    Call<ResponseObj<List<OrderDetailObj>>> orderDetail(@QueryMap Map<String,String> map);

    //获取商品分类
    @POST("api/App/GetGoodsType")
    Call<ResponseObj<List<CategoryObj>>> getGoodsCategory(@QueryMap Map<String,String> map);

    //新增商品分类
    @POST("api/App/AddGoodsType")
    Call<ResponseObj<Object>>addGoodsCategory(@QueryMap Map<String,String> map);

    //删除商品分类
    @POST("api/App/DeleteGoodsType")
    Call<ResponseObj<Object>> deleteGoodsCategory(@QueryMap Map<String,String> map);

    //修改商品分类
    @POST("api/App/UpdateGoodsType")
    Call<ResponseObj<Object>> updateGoodsCategory(@QueryMap Map<String,String> map);

    //获取订单列表
    @POST("api/App/GetOrderList")
    Call<ResponseObj<List<OrderListObj>>> orderList(@QueryMap Map<String,String> map);

    //抢单
    @POST("api/Order/GrabOrder")
    Call<ResponseObj<GradObj>> GrabOrderStep1(@QueryMap Map<String,String> map);

    //上报到店
    @POST("api/Order/takegoods")
    Call<ResponseObj<GradObj>> GrabOrderStep2(@QueryMap Map<String,String> map);

    //送达
    @POST("api/Order/completeOrder")
    Call<ResponseObj<GradObj>> GrabOrderStep3(@QueryMap Map<String,String> map);

    //钱包
    @POST("api/Account/GetMyWallet")
    Call<ResponseObj<WalletObj>> userWallet(@QueryMap Map<String,String> map);

    //账单列表
    @POST("api/Account/GetBillList")
    Call<ResponseObj<List<BillObj>>> userBillList(@QueryMap Map<String,String> map);

    //获取提现
    @POST("api/App/GetBalance")
    Call<ResponseObj<WithdrawalsObj>> Gettixian(@QueryMap Map<String,String> map);


    //提现操作
    @POST("api/App/GetWithdrawal")
    Call<ResponseObj<Object>> startWithdrawals(@QueryMap Map<String,String> map);

    //账单列表
    @POST("api/App/GetWithdrawal_order")
    Call<ResponseObj<List<MoneyDetailObj>>> getWithdrawalsList(@QueryMap Map<String,String> map);


    //图片上传
    @POST("api/App/PostUploadFileBase64")
    Call<ResponseObj<BaseObj>> uploadImg(@QueryMap Map<String, String> map, @Body UploadImgItem item);

    //实名认证
    @POST("api/Login/UploadCard")
    Call<ResponseObj<GradObj>> UploadCard(@QueryMap Map<String,String> map);



}
