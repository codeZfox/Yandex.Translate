package com.celt.translate.business.models;


import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.Date;

@Table
public class Translate {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public String source;

    @Column(indexed = true)
    public String translation;

    @Column(indexed = true)
    public String targetLang;

    @Column(indexed = true)
    public String sourceLang;

    @Column(indexed = true)
    public boolean isHistory;

    @Column(indexed = true)
    public Date dateHistory;

    @Column(indexed = true)
    public boolean isFavorite;

    @Column(indexed = true)
    public Date dateFavorite;

    public Translate() {
    }

    public Translate(String source, String translation, Lang sourceLang, Lang targetLang) {
        this.source = source;
        this.translation = translation;
        this.targetLang = targetLang.getCode();
        this.sourceLang = sourceLang.getCode();
        this.dateHistory = new Date();
        this.isHistory = true;
        this.dateFavorite = new Date();
    }

    @Override
    public String toString() {
        return "Translate{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", translation='" + translation + '\'' +
                ", targetLang='" + targetLang + '\'' +
                ", sourceLang='" + sourceLang + '\'' +
                ", isHistory=" + isHistory +
                ", dateHistory=" + dateHistory +
                ", isFavorite=" + isFavorite +
                ", dateFavorite=" + dateFavorite +
                '}';
    }

    public String textLang() {
        return (sourceLang + " - " + targetLang).toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Translate translate = (Translate) o;

        return id == translate.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
