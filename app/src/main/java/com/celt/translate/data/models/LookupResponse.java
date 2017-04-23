package com.celt.translate.data.models;

import com.celt.translate.data.models.dictionary.Def;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LookupResponse {
//    Не используется
//    @SerializedName("head")
//    public Head head;

    /**
     * Массив словарных статей
     */
    @SerializedName("def")
    public List<Def> def = new ArrayList<>();
}
