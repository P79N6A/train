package com.petzmall.training.module.category.network;

import com.github.retrofitutil.NoNetworkException;
import com.petzmall.training.Config;
import com.petzmall.training.base.BaseApiRequest;
import com.petzmall.training.base.MyCallBack;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {

    public static void getGoodsCategoryList(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getGoodsCategoryList(map).enqueue(callBack);
    }


}
