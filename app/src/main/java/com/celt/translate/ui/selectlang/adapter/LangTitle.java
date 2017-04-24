package com.celt.translate.ui.selectlang.adapter;

public class LangTitle implements LangSelectable {

    public LangTitle(String title) {
        this.title = title;
    }

    private String title;

    @Override
    public String getTitle() {
        return title;
    }
}
