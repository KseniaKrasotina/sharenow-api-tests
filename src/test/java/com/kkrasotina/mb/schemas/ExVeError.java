package com.kkrasotina.mb.schemas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ExVeError {
    public String exveErrorId;
    public String exveErrorMsg;
    public String exveErrorRef;

    public ExVeError() {
    }

    public static ExVeError fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, ExVeError.class);
    }

}
