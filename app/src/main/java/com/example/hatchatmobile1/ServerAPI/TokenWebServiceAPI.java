package com.example.hatchatmobile1.ServerAPI;

import com.example.hatchatmobile1.Entities.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TokenWebServiceAPI {
    @POST("Tokens")
    Call<String> getToken(@Body LoginRequest request);
}
