package com.petzmall.training;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.androidtools.SPUtils;
import com.github.retrofitutil.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;

public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
//最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可
        Observable.just(sharedPreferences.getString("session", ""))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String cookie) {
                        //添加cookie
                        builder.addHeader("Cookie", "JSESSIONID=CC663060DD46E01C3D95B3FDF05E9B1B");
                        Logger.i("add_xiang111  " + SPUtils.getPrefString(context,"session",""));
                    }
                });
        return chain.proceed(builder.build());
    }
}
