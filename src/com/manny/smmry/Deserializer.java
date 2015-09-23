package com.manny.smmry;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Deserializer {

    public static SMMRYAPI JsonDeserializer(String json) throws IOException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        
        return gson.fromJson(json, SMMRYAPI.class);
    }
}