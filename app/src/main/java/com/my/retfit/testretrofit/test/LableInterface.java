package com.my.retfit.testretrofit.test;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/11/29.
 */

public interface LableInterface {

    @GET("kehuduan/PAGE14501749764071042/index.json")
    Call<LableInfo> getLayoutInfo();

}
