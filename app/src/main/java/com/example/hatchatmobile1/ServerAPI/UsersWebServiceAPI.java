package com.example.hatchatmobile1.ServerAPI;

import com.example.hatchatmobile1.Entities.PostUserRequest;
import com.example.hatchatmobile1.Entities.PostUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsersWebServiceAPI {

    @POST("Users")
    Call<PostUserResponse> postNewUser(@Body PostUserRequest request);
}
