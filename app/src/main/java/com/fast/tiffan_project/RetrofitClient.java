package com.fast.tiffan_project;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitClient=null;
    public static final String BASE_URL ="https://webhook.site";

    public static Retrofit getInstance(){
        if(retrofitClient==null){
            return retrofitClient=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }else{
            return retrofitClient;
        }
    }
}
