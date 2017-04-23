package com.celt.translate.data.models.dictionary;

import com.google.gson.annotations.SerializedName;

/**
 * Синоним
 */
public class Syn {
    @SerializedName("text")
    private String text;

    /**
     * Часть речи (может отсутствовать).
     */
    @SerializedName("pos")
    private String pos;

    /**
     * Род существительного для тех языков, где это актуально (может отсутствовать)
     */
    @SerializedName("gen")
    private String gen;

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }

    public String getGen() {
        return gen;
    }
}
