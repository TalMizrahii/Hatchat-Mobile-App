package com.example.hatchatmobile1.ServerAPI;

public interface ServerResponse<T, E> {
    void onServerResponse(T response);

    void onServerErrorResponse(E error);
}

