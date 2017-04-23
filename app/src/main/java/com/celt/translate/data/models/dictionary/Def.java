package com.celt.translate.data.models.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Статья
 */
public class Def {

    @SerializedName("text")
    public String text;

    /**
     * Часть речи (может отсутствовать).
     */
    @SerializedName("pos")
    private String pos;

    /**
     * Транскрипция искомого слова.
     */
    @SerializedName("ts")
    private String ts;

    /**
     * Массив переводов.
     */
    @SerializedName("tr")
    public List<Tr> tr = new ArrayList<>();

    public String getText() {
        return text;
    }

    public String getTs() {
        return ts == null ? null : "[" + ts + "]";
    }

    public String getPos() {
        return pos;
    }
}