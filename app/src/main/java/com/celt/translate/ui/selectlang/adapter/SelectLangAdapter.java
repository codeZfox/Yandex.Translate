package com.celt.translate.ui.selectlang.adapter;

import com.celt.translate.business.models.Lang;
import com.celt.translate.ui.base.OnClickItemListener;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.List;

public class SelectLangAdapter extends ListDelegationAdapter<List<LangSelectable>> {

    private OnClickItemListener<Lang> onClickLister;
    private Lang currantLang;

    public SelectLangAdapter(OnClickItemListener<Lang> onClickLister, Lang currantLang) {
        this.onClickLister = onClickLister;
        this.currantLang = currantLang;

        delegatesManager.addDelegate(new LangAdapterDelegate(onClickLister, currantLang))
                .addDelegate(new TitleDelegate());

    }

    public void setOnClickLister(OnClickItemListener<Lang> onClickLister) {
        this.onClickLister = onClickLister;
    }

    public void setCurrantLang(Lang currantLang) {
        this.currantLang = currantLang;

    }
}
