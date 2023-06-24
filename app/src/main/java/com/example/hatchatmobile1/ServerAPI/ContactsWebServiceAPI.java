package com.example.hatchatmobile1.ServerAPI;

import com.example.hatchatmobile1.Entities.AllChatResponse;
import com.example.hatchatmobile1.Entities.AllMessagesResponse;
import com.example.hatchatmobile1.Entities.ContactChatResponse;
import com.example.hatchatmobile1.Entities.MessageRequest;
import com.example.hatchatmobile1.Entities.MessageResponse;
import com.example.hatchatmobile1.Entities.NewContactChatRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface ContactsWebServiceAPI {
    @POST("Chats")
    @Headers({"Content-Type: application/json"})
    Call<ContactChatResponse> AddContactChat(@Body NewContactChatRequest newContactChatRequest, @retrofit2.http.Header("Authorization") String token);

    @GET("Chats/{id}/Messages")
    @Headers({"Content-Type: application/json"})
    Call<List<MessageResponse>> GetMessagesForContact(@retrofit2.http.Header("Authorization") String token, @Path("id") int contactId);

    @GET("Chats")
    @Headers({"Content-Type: application/json"})
    Call<List<AllChatResponse>> GetAllChats(@retrofit2.http.Header("Authorization") String token);

    @POST("Chats/{id}/Messages")
    @Headers({"Content-Type: application/json"})
    Call<MessageResponse> AddNewMessageToChat(@retrofit2.http.Header("Authorization") String token,
                                              @Path("id") int chatId,
                                              @Body MessageRequest messageRequest);

    @GET("Chats/{id}")
    @Headers({"Content-Type: application/json"})
    Call<AllMessagesResponse> GetAllMessagesById(@retrofit2.http.Header("Authorization") String token,
                                                 @Path("id") int chatId);
}
