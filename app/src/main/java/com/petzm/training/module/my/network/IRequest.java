package com.petzm.training.module.my.network;


import com.petzm.training.base.ResponseObj;
import com.petzm.training.module.my.bean.Lucky;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {

    @GET("luckydraw")
    Call<ResponseObj<Lucky>> getLuckdraw(@QueryMap Map<String, String> map);

}
