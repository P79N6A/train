package com.petzmall.training.module.socialCircle.network;

import com.github.retrofitutil.NoNetworkException;
import com.petzmall.training.Config;
import com.petzmall.training.base.BaseApiRequest;
import com.petzmall.training.base.MyCallBack;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {

    public static void getCircleData(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getCircleData(map).enqueue(callBack);
    }


    public static void getVideoData(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getVideoData(map).enqueue(callBack);
    }


    public static void getCommentData(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getCommentData(map).enqueue(callBack);
    }

    public static void getSecondComments(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getSecondComments(map).enqueue(callBack);
    }


}
