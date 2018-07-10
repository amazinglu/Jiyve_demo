package com.example.amazinglu.jiyve_demo.Util;

import com.example.amazinglu.jiyve_demo.Model.Restaurant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelUtil {

    private static Gson gsonForSerialize = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .create();

    private static Gson gsonForDeserialize = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateDeserializer())
            .create();

    public static <T> T toObject(String json, TypeToken<T> typeToken) {
        return gsonForDeserialize.fromJson(json, typeToken.getType());
    }

    public static String toJson(Object object) {
        return gsonForSerialize.toJson(object);
    }

    public static ArrayList<String> toJsonList(List<Restaurant> objectList) {
        ArrayList<String> jsonList = new ArrayList<>();
        for (Object object : objectList) {
            jsonList.add(toJson(object));
        }
        return jsonList;
    }

    public static <T> List<T> toObjectList(List<String> jsonList, TypeToken<T> tTypeToken) {
        List<T> objectList = new ArrayList<>();
        for (String jsonStr : jsonList) {
            objectList.add(toObject(jsonStr, tTypeToken));
        }
        return objectList;
    }


    static class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement dateStr, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return DateUtil.stringToDateGson(dateStr.getAsString());
        }
    }

    static class DateSerializer implements JsonSerializer<Date> {
        @Override
        public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(DateUtil.dateToStringGson(date));
        }
    }
}
