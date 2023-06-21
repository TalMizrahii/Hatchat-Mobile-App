package com.example.hatchatmobile1.ServerAPI;

import com.example.hatchatmobile1.Entities.ContactChatResponse;
import com.example.hatchatmobile1.Entities.NewContactChatRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.DELETE;

public interface ContactsWebServiceAPI {
    @POST("Chats")
    @Headers({"Content-Type: application/json"})
    Call<ContactChatResponse> AddContactChat(@Body NewContactChatRequest newContactChatRequest, @retrofit2.http.Header("Authorization") String token);
}