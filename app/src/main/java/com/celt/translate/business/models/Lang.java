package com.celt.translate.business.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Lang implements Comparable<Lang>, Parcelable {

    public static final String NAME = "LANG";

    private String code;
    private String ui;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lang lang = (Lang) o;

        return code.equals(lang.code);

    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public int compareTo(@NonNull Lang o) {
        return this.ui.compareTo(o.ui);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.ui);
    }

    protected Lang(Parcel in) {
        this.code = in.readString();
        this.ui = in.readString();
    }

    public static final Parcelable.Creator<Lang> CREATOR = new Parcelable.Creator<Lang>() {
        @Override
        public Lang createFromParcel(Parcel source) {
            return new Lang(source);
        }

        @Override
        public Lang[] newArray(int size) {
            return new Lang[size];
        }
    };
}
