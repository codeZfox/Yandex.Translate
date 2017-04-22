package com.celt.translate.business.models;

import android.support.annotation.NonNull;
import com.celt.translate.data.models.LangsResponse;

public class Lang implements Comparable<Lang>{
    private String code;
    private String ui;

    public Lang(LangsResponse item) {

    }

    public Lang(String key, String value) {
        this.code = key;
        this.ui = value;
    }

    public String getCode() {
        return code;
    }

    public String getUi() {
        return ui;
    }

    @Override
    public int compareTo(@NonNull Lang o) {
        return this.ui.compareTo(o.ui);
    }
}
