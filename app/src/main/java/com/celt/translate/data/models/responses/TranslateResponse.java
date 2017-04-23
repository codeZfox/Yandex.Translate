package com.celt.translate.data.models.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TranslateResponse {

    @SerializedName("code")
    private Integer code;

    @SerializedName("lang")
    private String lang;

    @SerializedName("text")
    private List<String> text = new ArrayList<>();

    public Integer getCode() {
        return code;
    }

    public String getLang() {
        return lang;
    }

    public List<String> getText() {
        return text;
    }
}
