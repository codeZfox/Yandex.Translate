package com.celt.translate.ui.selectlang;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.celt.translate.ui.selectlang.adapter.LangSelectable;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SelectLangView extends MvpView {

    void setLangs(List<LangSelectable> langs);

}
