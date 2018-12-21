package com.petzm.training.module.socialCircle.network;


import com.petzm.training.base.ResponseObj;
import com.petzm.training.module.home.bean.HomeObj;
import com.petzm.training.module.socialCircle.bean.CommentsBean;
import com.petzm.training.module.socialCircle.bean.SecondCommentBean;
import com.petzm.training.module.socialCircle.bean.Video;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {

    @GET("circle")
    Call<ResponseObj<HomeObj>> getCircleData(@QueryMap Map<String, String> map);

    @GET("video")
    Call<ResponseObj<Video>> getVideoData(@QueryMap Map<String, String> map);

    @GET("comment")
    Call<ResponseObj<CommentsBean>> getCommentData(@QueryMap Map<String, String> map);

    @GET("secondComment")
    Call<ResponseObj<SecondCommentBean>> getSecondComments(@QueryMap Map<String, String> map);

}
