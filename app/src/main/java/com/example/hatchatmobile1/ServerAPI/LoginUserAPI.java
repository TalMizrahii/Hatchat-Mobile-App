package com.example.hatchatmobile1.ServerAPI;

import android.content.Context;

import androidx.annotation.NonNull;

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

        this.settingsViewModal = new SettingsViewModal(context);

        baseUrl = settingsViewModal.getSettings().getBaseUrl();

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        userWebServiceAPI = retrofit.create(TokenWebServiceAPI.class);
    }

    public void getToken(String username, String password, final TokenCallback callback) {
        LoginRequest request = new LoginRequest(username, password);

        Call<String> call = userWebServiceAPI.getToken(request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    if (token != null) {
                        // Token retrieval successful
                        callback.onTokenReceived(token);
                    } else {
                        // Handle null response
                        callback.onTokenError("Null response");
                    }
                } else {
                    // Handle unsuccessful response
                    callback.onTokenError("Invalid username/password");
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                // Handle failure
                callback.onTokenError(t.getMessage());
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

    public interface TokenCallback {
        void onTokenReceived(String token);

        void onTokenError(String error);
    }
}
