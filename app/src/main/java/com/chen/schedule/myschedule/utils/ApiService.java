package com.chen.schedule.myschedule.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
/**
 * 2017年创建
 * @author 陈鑫
 */
public interface ApiService {

    public static String SERVICE_URL=GlobalContants.SERVER_URL;

    @GET("{downUrl}")
    Call<ResponseBody> dowmApk(@Path("downUrl") String url);
}
