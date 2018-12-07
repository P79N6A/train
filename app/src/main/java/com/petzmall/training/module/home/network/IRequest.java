package com.petzmall.training.module.home.network;


import com.petzmall.training.base.ResponseObj;
import com.petzmall.training.module.home.bean.HomeObj;
import com.petzmall.training.module.home.bean.RefreshObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {

    //首页
//    @POST("console/outer/page/home")
    @GET("home")
    Call<ResponseObj<HomeObj>> getHomeData();


    //换一换
    @POST("refresh")
    Call<ResponseObj<RefreshObj>> getRefreshData(@QueryMap Map<String, String> map);

}
