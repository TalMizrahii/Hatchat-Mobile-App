package com.example.hatchatmobile1.ServerAPI;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.hatchatmobile1.Entities.PostUserRequest;
import com.example.hatchatmobile1.Entities.UsersResponse;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersAPI {

    private Retrofit retrofit;
    private UsersWebServiceAPI userWebServiceAPI;
    private String baseUrl;
    private Gson gson;
    private SettingsViewModal settingsViewModal;

    public UsersAPI(@NonNull Context context) {
        this.settingsViewModal = SettingsViewModal.getInstance(context);
        baseUrl = settingsViewModal.getSettings().getBaseUrl();
        gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        userWebServiceAPI = retrofit.create(UsersWebServiceAPI.class);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        userWebServiceAPI = retrofit.create(UsersWebServiceAPI.class);
    }

    public void postNewUser(String username, String password, String displayName, String profilePic, final ServerResponse<UsersResponse, String> callback) {
        PostUserRequest request = new PostUserRequest(username, password, displayName, profilePic);
        Call<UsersResponse> call = userWebServiceAPI.postNewUser(request);
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(@NonNull Call<UsersResponse> call, @NonNull Response<UsersResponse> response) {
                if (response.isSuccessful()) {
                    // Process the successful response
                    UsersResponse userResponse = response.body();
                    callback.onServerResponse(userResponse);
                } else {
                    // Handle unsuccessful response
                    callback.onServerErrorResponse("Conflict detected!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UsersResponse> call, @NonNull Throwable t) {
                // Handle failure
                callback.onServerErrorResponse(t.getMessage());
            }
        });
    }


    public void getUserByUsername(String username, String token, final ServerResponse<UsersResponse, String> callback) {

        Call<UsersResponse> call = userWebServiceAPI.getUserByUsername(token, username);
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(@NonNull Call<UsersResponse> call, @NonNull Response<UsersResponse> response) {
                if (response.isSuccessful()) {
                    // Process the successful response
                    UsersResponse userResponse = response.body();
                    callback.onServerResponse(userResponse);
                } else {
                    // Handle unsuccessful response
                    callback.onServerErrorResponse("Unauthorized!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UsersResponse> call, @NonNull Throwable t) {
                // Handle failure
                callback.onServerErrorResponse(t.getMessage());
            }
        });
    }




}
