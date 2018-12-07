package com.petzmall.training.module.course.network;

import com.petzmall.training.base.ResponseObj;
import com.petzmall.training.module.course.bean.SearchRecordObj;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface IRequest {

    //历史搜索记录
    @GET("api/HomePage/GetHottestSearch")
    Call<ResponseObj<SearchRecordObj>> getSearchRecord(@QueryMap Map<String,String> map);
}