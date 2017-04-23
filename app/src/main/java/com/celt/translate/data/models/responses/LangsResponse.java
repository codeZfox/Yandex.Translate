package com.celt.translate.data.models.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LangsResponse {
    public LangsResponse() {
        dirs = new ArrayList();
        langs = new HashMap<>();
    }

    @SerializedName("dirs")
    private List dirs;

    @SerializedName("langs")
    private HashMap<String, String> langs;

    public HashMap<String, String> getLangs() {
        return langs;
    }
}