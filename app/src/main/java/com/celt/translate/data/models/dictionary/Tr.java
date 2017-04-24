package com.celt.translate.data.models.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Перевод.
 */
public class Tr {

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

    /**
     * Массив синонимов.
     */
    @SerializedName("syn")
    private List<Syn> syn = new ArrayList<>();

    /**
     * Массив значений.
     */
    @SerializedName("mean")
    private List<Mean> mean = new ArrayList<>();

    /**
     * Массив примеров.
     */
    @SerializedName("ex")
    private List<Ex> ex = new ArrayList<>();

    public String getText() {
        return text;
    }

    public String getGen() {
        return gen;
    }


    public boolean isSyn() {
        return !syn.isEmpty();
    }


    public List<Syn> getSyn() {
        return syn;
    }

    public boolean isMean() {
        return !mean.isEmpty();
    }

    public String getMean() {
        StringBuilder builder = new StringBuilder();

        builder.append("(");
        for (int i = 0; i < mean.size(); i++) {
            builder.append(mean.get(i).getText());
            if (i < mean.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(")");

        return builder.toString();
    }

    public boolean isEx() {
        return !ex.isEmpty();
    }

    public List<Ex> getEx() {
        return ex;
    }


}
