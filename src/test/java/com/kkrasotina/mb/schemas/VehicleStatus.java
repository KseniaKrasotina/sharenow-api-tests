package com.kkrasotina.mb.schemas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class VehicleStatus {
    public Resource decklidstatus;
    public Resource doorstatusfrontleft;
    public Resource doorstatusfrontright;
    public Resource doorstatusrearleft;
    public Resource doorstatusrearright;
    public Resource interiorLightsFront;
    public Resource rooftopstatus;

    public VehicleStatus() {
    }

    public static VehicleStatus fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, VehicleStatus.class);
    }
}
