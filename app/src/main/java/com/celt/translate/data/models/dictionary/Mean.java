package com.celt.translate.data.models.dictionary;

import com.google.gson.annotations.SerializedName;

/**
 * Значение
 */
public class Mean {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }
}
