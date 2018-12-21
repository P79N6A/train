package com.petzm.training.module.my.network;

import com.github.retrofitutil.NoNetworkException;
import com.petzm.training.Config;
import com.petzm.training.base.BaseApiRequest;
import com.petzm.training.base.MyCallBack;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {

    public static void getLuckdraw(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getLuckdraw(map).enqueue(callBack);
    }





}
