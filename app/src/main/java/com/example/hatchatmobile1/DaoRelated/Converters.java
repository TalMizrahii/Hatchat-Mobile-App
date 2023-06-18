package com.example.hatchatmobile1.DaoRelated;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Class to convert non-primitive data types for Room database.
 */
public class Converters {
    @TypeConverter
    public static List<Message> fromString(String value) {
        Type listType = new TypeToken<List<Message>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Message> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
