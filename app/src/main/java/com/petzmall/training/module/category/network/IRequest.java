package com.petzmall.training.module.category.network;


import com.petzmall.training.base.ResponseObj;
import com.petzmall.training.module.category.bean.CategoryObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //获取商品分类
    @GET("category")
    Call<ResponseObj<CategoryObj>> getGoodsCategoryList(@QueryMap Map<String, String> map);

}
