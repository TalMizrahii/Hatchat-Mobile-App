package com.example.hatchatmobile1.ServerAPI;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.hatchatmobile1.Entities.PostUserRequest;
import com.example.hatchatmobile1.Entities.PostUserResponse;
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
        this.settingsViewModal = new SettingsViewModal(context);
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

    public void postNewUser(String username, String password, String displayName, String profilePic, final UsersResponse callback) {
        PostUserRequest request = new PostUserRequest(username, password, displayName, profilePic);
        Call<PostUserResponse> call = userWebServiceAPI.postNewUser(request);
        call.enqueue(new Callback<PostUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<PostUserResponse> call, @NonNull Response<PostUserResponse> response) {
                if (response.isSuccessful()) {
                    // Process the successful response
                    PostUserResponse userResponse = response.body();
                    callback.onUserResponse(userResponse);
                } else {
                    // Handle unsuccessful response
                    callback.onUserError("Conflict detected!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostUserResponse> call, @NonNull Throwable t) {
                // Handle failure
                callback.onUserError(t.getMessage());
            }
        });
    }


    public interface UsersResponse {
        void onUserResponse(PostUserResponse userResponse);

        void onUserError(String error);
    }

}
