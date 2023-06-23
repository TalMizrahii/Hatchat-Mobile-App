package com.example.hatchatmobile1.ServerAPI;

import com.example.hatchatmobile1.Entities.PostUserRequest;
import com.example.hatchatmobile1.Entities.UsersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsersWebServiceAPI {

    @POST("Users")
    Call<UsersResponse> postNewUser(@Body PostUserRequest request);

    @GET("Users/{username}")
    @Headers({"Content-Type: application/json"})
    Call<UsersResponse> getUserByUsername(@retrofit2.http.Header("Authorization") String token,
                                          @Path("username") String username);


}
