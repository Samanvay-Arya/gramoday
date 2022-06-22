package com.samanvay.gramody.Interface;

import com.samanvay.gramody.Model.JsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonInterface {
    @GET("v1/user/bySlug?profileSlug=mmtradingco")
    Call<JsonResponse> getData();
}
