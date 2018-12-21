package com.petzm.training.module.course.network;

import com.github.retrofitutil.NoNetworkException;
import com.petzm.training.Config;
import com.petzm.training.base.BaseApiRequest;
import com.petzm.training.base.MyCallBack;

import java.util.Map;

public class ApiRequest extends BaseApiRequest{

    public static void getSearchRecord(Map map , MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getSearchRecord(map).enqueue(callBack);
    }
}
