package com.petzm.training.module.home.network;


import com.petzm.training.base.ResponseObj;
import com.petzm.training.module.home.bean.HomeObj;
import com.petzm.training.module.home.bean.RefreshObj;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {

    //首页
//    @POST("console/outer/page/home")
    @POST("/console/outer/page/home")
    Call<ResponseObj<HomeObj>> getHomeData();


    //换一换
    @POST("refresh")
    Call<ResponseObj<RefreshObj>> getRefreshData(@QueryMap Map<String, String> map);

}
