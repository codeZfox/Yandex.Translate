package com.celt.translate.data.models.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Пример.
 */
public class Ex {

    @SerializedName("text")
    public String text;

    @SerializedName("tr")
    public List<Tr_> tr = new ArrayList<>();

    public String getText() {
        return text;
    }

    public String getTr() {
        StringBuilder builder = new StringBuilder();

        builder.append(text);
        builder.append(" — ");
        for (int i = 0; i < tr.size(); i++) {
            builder.append(tr.get(i).getText());
            if (i < tr.size() - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }


}
