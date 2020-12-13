package com.systemvx.androiddevtest;

import android.app.DownloadManager;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public static final String BASE_URL="";
    private static Map<String, List<Cookie>>cookieStore= new HashMap<>()
    private static ExecutorService thredPool= Executors.newFixedThreadPool(30);
    private static OkHttpClient okHttpClient= new OkHttpClient.Builder().cookieJar(new CookieJar()
    {
        @Override
        public void saveFromResponse(@NonNull HttpUrl httpUrl,
                                     @NonNull List<Cookie>list)
        {cookieStore.put(httpUrl.host(),list);
        }
        @Override
        public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl)
        {
            List<Cookie> cookies=cookieStore.get(httpUrl.host());
            return cookies ==null ? new ArrayList<>():cookies;
        }
    }).build();
    public static <Call> String getRequest(String url) throws Exception
    {
        FutureTask<String> task =new FutureTask<>(()->{
            DownloadManager.Request request=new Request.Builder()
                    .url(url)
                    .build();
            Call call =okHttpClient.newCall(request);
            Response response=call.execute();
            if (response.isSuccessful()&& response.body()!=null)
            {
                return response.body().string().trim();
            }
            else
            {
                return null;
            }
        });
        threadPool.submit(task);
        return task.get();
    }
    public static String postRequest(String url,
                                     Map<String,String> rawParams) throws Exception
    {
        FutureTask<String>task=new FutureTask<>(()->{
            FormBody.Builder builder=new Formbody.Builder();
            rawParams.forEach(builder::add);
            FormBody body= builder.build();
            Request request=new Request.Builder()
                    .url(url)
                    .post(body).build();
            Call call=okHttpClient.newCall(request);
            Response response=call.excute();
            if(response.isSuccessful()&& response.body!=null)
            {
                return response.body().string().trim()
            }
            else
            {
                return null;
            }
        });
        thredPool.submit(task);
        return task.get();
    }
}
