package com.example.hatchatmobile1.ServerAPI;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.example.hatchatmobile1.Entities.LoginRequest;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUserAPI {
    private Retrofit retrofit;
    private TokenWebServiceAPI userWebServiceAPI;
    private String baseUrl;
    private Gson gson;
    private SettingsViewModal settingsViewModal;


    public LoginUserAPI(@NonNull Context context) {

        this.settingsViewModal = SettingsViewModal.getInstance(context);

        baseUrl = settingsViewModal.getSettings().getBaseUrl();

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        userWebServiceAPI = retrofit.create(TokenWebServiceAPI.class);

        settingsViewModal.getSettingsLiveData().observeForever(settings -> {
            setBaseUrl(settings.getBaseUrl());
        });
    }

    public void getToken(String username, String password, final ServerResponse<String, String> callback) {
        LoginRequest request = new LoginRequest(username, password);

        Call<String> call = userWebServiceAPI.getToken(request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    if (token != null) {
                        // Token retrieval successful
                        callback.onServerResponse(token);
                    } else {
                        // Handle null response
                        callback.onServerErrorResponse("Null response");
                    }
                } else {
                    // Handle unsuccessful response
                    callback.onServerErrorResponse("Invalid username/password");
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                // Handle failure
                callback.onServerErrorResponse(t.getMessage());
            }
        });
    }


    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        userWebServiceAPI = retrofit.create(TokenWebServiceAPI.class);

    }

}

