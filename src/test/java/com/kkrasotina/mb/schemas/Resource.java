package com.kkrasotina.mb.schemas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Resource {
    public Long timestamp;
    public String value;

    public static Resource fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, Resource.class);
    }
}
