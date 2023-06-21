package com.example.hatchatmobile1.ServerAPI;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.hatchatmobile1.Entities.ContactChatResponse;
import com.example.hatchatmobile1.Entities.NewContactChatRequest;
import com.example.hatchatmobile1.Entities.User;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {

    private Retrofit retrofit;

    private SettingsViewModal settingsViewModal;

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


    public void postNewContactChat(String username, final OnContactChatResponseListener listener) {
        Call<ContactChatResponse> call = contactsWebServiceAPI.AddContactChat(new NewContactChatRequest(username), token);
        call.enqueue(new Callback<ContactChatResponse>() {
            @Override
            public void onResponse(@NonNull Call<ContactChatResponse> call, @NonNull Response<ContactChatResponse> response) {
                if (response.isSuccessful()) {
                    ContactChatResponse contactChatResponse = response.body();
                    listener.onResponse(contactChatResponse); // Pass the response to the listener
                } else {
                    listener.onError("Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ContactChatResponse> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public interface OnContactChatResponseListener {
        void onResponse(ContactChatResponse contactChatResponse);

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
}
