package com.my.retfit.testretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.my.retfit.testretrofit.test.LableInfo;
import com.my.retfit.testretrofit.test.LableInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//上传代码的时候，先更新
//        我就修改了一个文字而已
//        添加了两行注释


//        testRetrofit();

//        拉拉拉 啦啦啦啦 我是卖报的小行家
//        分支second

        testRxJava();
    }


    public void testRxJava(){




    }



















    public void testRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.ipanda.com/")
                .build();

        LableInterface lableInterface =  retrofit.create(LableInterface.class);

        Call<LableInfo> call =lableInterface.getLayoutInfo();
        call.enqueue(new Callback<LableInfo>() {
            @Override
            public void onResponse(Call<LableInfo> call, Response<LableInfo> response) {

                LableInfo lableInfo = response.body();

                Log.e("TAG","----网络请求成功-----");
                Log.e("TAG","返回值："+lableInfo.getData().getPandalive().toString());
                Log.e("TAG","----网络请求成功-----");

            }

            @Override
            public void onFailure(Call<LableInfo> call, Throwable t) {
                Log.e("TAG","----网络请求失败-----");
            }
        });



//       testRetrofit Retrofit retrofit = new Retrofit.Builder().baseUrl("").addConverterFactory()





    }



}
