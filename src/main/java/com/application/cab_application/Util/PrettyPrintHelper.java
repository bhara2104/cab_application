package com.application.cab_application.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class PrettyPrintHelper {
    public static JsonElement prettyPrintHelper(Object val){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJsonTree(val);
    }
}
