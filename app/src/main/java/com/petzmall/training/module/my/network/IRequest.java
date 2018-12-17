package com.petzmall.training.module.my.network;


import com.petzmall.training.base.ResponseObj;
import com.petzmall.training.module.home.bean.HomeObj;
import com.petzmall.training.module.my.bean.Lucky;
import com.petzmall.training.module.socialCircle.bean.CommentsBean;
import com.petzmall.training.module.socialCircle.bean.SecondCommentBean;
import com.petzmall.training.module.socialCircle.bean.Video;

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
