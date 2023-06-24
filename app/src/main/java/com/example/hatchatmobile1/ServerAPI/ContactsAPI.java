package com.example.hatchatmobile1.ServerAPI;


import androidx.annotation.NonNull;

import com.example.hatchatmobile1.Entities.AllChatResponse;
import com.example.hatchatmobile1.Entities.AllMessagesResponse;
import com.example.hatchatmobile1.Entities.ContactChatResponse;
import com.example.hatchatmobile1.Entities.MessageRequest;
import com.example.hatchatmobile1.Entities.MessageResponse;
import com.example.hatchatmobile1.Entities.NewContactChatRequest;
import com.example.hatchatmobile1.Entities.UsersResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {

    private Retrofit retrofit;
    private ContactsWebServiceAPI contactsWebServiceAPI;
    private String baseUrl;
    private Gson gson;
    private String token;

    public ContactsAPI(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        contactsWebServiceAPI = retrofit.create(ContactsWebServiceAPI.class);
    }

    public ContactChatResponse postNewContactChat(String username) throws IOException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Response<ContactChatResponse>> future = executor.submit(() -> {
            Call<ContactChatResponse> call = contactsWebServiceAPI.AddContactChat(new NewContactChatRequest(username), token);
            return call.execute();
        });
        try {
            Response<ContactChatResponse> response = future.get();
            if (response != null && response.isSuccessful()) {
                return response.body();
            } else {
                throw new IOException("Request failed with code: " + response.code());
            }
        } catch (Exception e) {
            throw new IOException("Error executing the request: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }


    public void getMessagesForContact(int contactId, final OnGetMessagesResponseListener listener) {
        Call<List<MessageResponse>> call = contactsWebServiceAPI.GetMessagesForContact(token, contactId);
        call.enqueue(new Callback<List<MessageResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MessageResponse>> call, @NonNull Response<List<MessageResponse>> response) {
                if (response.isSuccessful()) {
                    List<MessageResponse> messages = response.body();
                    if (messages != null) {
                        listener.onResponse(messages); // Pass the messages to the listener
                    } else {
                        listener.onError("Response body is empty");
                    }
                } else {
                    listener.onError("Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MessageResponse>> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }


    public void getAllChats(final OnGetAllChatsResponseListener listener) {
        Call<List<AllChatResponse>> call = contactsWebServiceAPI.GetAllChats(token);
        call.enqueue(new Callback<List<AllChatResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<AllChatResponse>> call, @NonNull Response<List<AllChatResponse>> response) {
                if (response.isSuccessful()) {
                    List<AllChatResponse> chats = response.body();
                    if (chats != null) {
                        listener.onResponse(chats); // Pass the chats to the listener
                    } else {
                        listener.onError("Response body is empty");
                    }
                } else {
                    listener.onError("Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AllChatResponse>> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }


    public void AddMessage(MessageRequest messageRequest, int chatId, final ServerResponse<MessageResponse, String> callback) {
        Call<MessageResponse> call = contactsWebServiceAPI.AddNewMessageToChat(token, chatId, messageRequest);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                MessageResponse messageResponse = response.body();
                callback.onServerResponse(messageResponse);
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                callback.onServerErrorResponse(t.getMessage());
            }
        });
    }

    public void GetAllMessagesById(int chatId, final ServerResponse<AllMessagesResponse, String> callback) {
        Call<AllMessagesResponse> call = contactsWebServiceAPI.GetAllMessagesById(token, chatId);
        call.enqueue(new Callback<AllMessagesResponse>() {
            @Override
            public void onResponse(Call<AllMessagesResponse> call, Response<AllMessagesResponse> response) {
                AllMessagesResponse messages = response.body();
                callback.onServerResponse(messages);
            }

            @Override
            public void onFailure(Call<AllMessagesResponse> call, Throwable t) {
                callback.onServerErrorResponse(t.getMessage());
            }
        });
    }

    public interface OnGetAllChatsResponseListener {
        void onResponse(List<AllChatResponse> chats);

        void onError(String error);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        contactsWebServiceAPI = retrofit.create(ContactsWebServiceAPI.class);
    }


    public interface OnGetMessagesResponseListener {
        void onResponse(List<MessageResponse> messages);

        void onError(String error);
    }

}
