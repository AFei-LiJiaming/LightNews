package com.AFei.LightNews.service;

import android.util.Log;

import com.Blinger.base.utils.LogUtils;
import com.AFei.LightNews.config.Config;
import com.AFei.LightNews.config.Constant;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//RetrofitManager管理器的创建，保证Retrofit在类中只有一个实例，避免请求体的多次创建
public class RetrofitManager
{
    private static RetrofitManager mManager;
    private Retrofit mRetrofit;
    private final String HEADER_CONNECTION = "keep-alive";
    private static final long DEFAULT_TIMEOUT = 60L;

    private RetrofitManager()
    {
    }


    public static RetrofitManager getManager()
    {
        if (mManager == null)
        {
            synchronized (RetrofitManager.class)
            {
                if (mManager == null)
                {
                    mManager = new RetrofitManager();
                }
            }
        }
        return mManager;
    }


    /**
     * 初始化Retrofit
     * @return
     */
    public Retrofit getRetrofit()
    {
        if (mRetrofit == null)
        {
            synchronized (RetrofitManager.class)
            {
                if (mRetrofit == null)
                {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    //添加请求拦截(可以在此处打印请求信息和响应信息)
                    builder.addInterceptor(new MutiBaseUrlInterceptor())
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                    mRetrofit = new Retrofit.Builder()
                            .client(builder.build())
                            //基础URL 建议以 / 结尾
                            .baseUrl(Config.BASE_URL)
                            //设置 Json 转换器
                            .addConverterFactory(GsonConverterFactory.create())
                            //RxJava 适配器
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                            .build();
                }
            }
        }
        return mRetrofit;
    }

    /**
     * 设置公共查询参数
     */
    public class CommonQueryParamsInterceptor implements Interceptor
    {
        @Override
        public Response intercept(Chain chain) throws IOException
        {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("paramsA", "a")
                    .addQueryParameter("paramsB", "b")
                    .build();
            return chain.proceed(request.newBuilder().url(url).build());
        }
    }

    /**
     * 添加请求头需要携带的参数
     */
    public class HeaderInterceptor implements Interceptor
    {
        @Override
        public Response intercept(Chain chain) throws IOException
        {
//            Request request = chain.request();
//            TokenBean token = App.mToken;
//            Request requestBuilder = request.newBuilder()
//                    .addHeader("Connection", HEADER_CONNECTION)
//                    .addHeader("Authorization", token.getToken())
//                    .method(request.method(), request.body())
//                    .build();
//            return chain.proceed(requestBuilder);
            return null;
        }
    }

    /**
     * 设置缓存
     */
    public class CacheInterceptor implements Interceptor
    {

        @Override
        public Response intercept(Chain chain) throws IOException
        {
            Request request = chain.request();
            request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            int maxAge = 0;
            // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Retrofit")
                    .build();
            // 无网络时，设置超时为4周  只对get有用,post没有缓冲
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("nyn")
                    .build();
            return response;
        }
    }


    /**
     * 日志d打印
     */
    public class LoggingInterceptor implements Interceptor
    {
        @Override
        public Response intercept(Chain chain) throws IOException
        {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogUtils.i(content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }

    }

    /**
     * 打印log日志：该拦截器用于记录应用中的网络请求的信息
     */
    private HttpLoggingInterceptor getHttpLogingInterceptor()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger()
        {
            @Override
            public void log(String message)
            {
                //包含所有的请求信息
                //如果收到响应是json才打印
                if ("{".equals(message) || "[".equals(message))
                {
                    Log.d("TAG", "收到响应: " + message);
                }
                Log.d("TAG", "message=" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

//    private String BASE_URL_OTHER = "http://wthrcdn.etouch.cn/";

    /**
     * 添加可以处理多个Baseurl的拦截器：http://blog.csdn.net/qq_36707431/article/details/77680252
     * Retrofit(OKHttp)多BaseUrl情况下url实时自动替换完美解决方法:https://www.2cto.com/kf/201708/663977.html
     * <p>
     * //     http://wthrcdn.etouch.cn/weather_mini?city=北京
     * //    @Headers({"url_name:other"})
     * //    @GET("weather_mini")
     * //    Observable<WeatherEntity> getMessage(@Query("city") String city);
     */
    private class MutiBaseUrlInterceptor implements Interceptor
    {

        @Override
        public Response intercept(Chain chain) throws IOException
        {
            //获取request
            Request request = chain.request();
            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();
            //获取request的创建者builder
            Request.Builder builder = request.newBuilder();
            //从request中获取headers，通过给定的键url_name
            List<String> headerValues = request.headers("url_name");
            if (headerValues != null && headerValues.size() > 0)
            {
                //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                builder.removeHeader("url_name");
                //匹配获得新的BaseUrl
                String headerValue = headerValues.get(0);
                HttpUrl newBaseUrl = null;
                if ("other".equals(headerValue))
                {
                    newBaseUrl = HttpUrl.parse(Config.BASE_URL_OTHER);
                } else if ("news".equals(headerValue)) {
                    newBaseUrl = HttpUrl.parse(Config.BASE_URL);
                } else
                {
                    newBaseUrl = oldHttpUrl;
                }
                //在oldHttpUrl的基础上重建新的HttpUrl，修改需要修改的url部分
                HttpUrl newFullUrl = oldHttpUrl
                        .newBuilder()
                        .scheme(newBaseUrl.scheme())//更换网络协议,根据实际情况更换成https或者http
                        .host(newBaseUrl.host())//更换主机名
                        .port(newBaseUrl.port())//更换端口
                        //.removePathSegment(0)//移除第一个参数v1
                        .build();
                //重建这个request，通过builder.url(newFullUrl).build()；
                // 然后返回一个response至此结束修改
                LogUtils.d(Constant.debugName, "intercept: " + newFullUrl.toString());
                return chain.proceed(builder.url(newFullUrl).build());
            }
            return chain.proceed(request);

        }
    }

    /**
     * Retrofit上传文件
     *
     * @param mImagePath
     * @return
     */
    public RequestBody getUploadFileRequestBody(String mImagePath)
    {
        File file = new File(mImagePath);
        //构建body
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        return body;
    }
}


