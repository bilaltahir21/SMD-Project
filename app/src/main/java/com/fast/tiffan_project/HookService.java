package com.fast.tiffan_project;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HookService {
    @GET("/d0226baa-5937-4a87-b684-dc30d3b3140b")
    Call<Version> getHook();
}
