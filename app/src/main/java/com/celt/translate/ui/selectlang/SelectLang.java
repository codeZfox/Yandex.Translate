package com.celt.translate.ui.selectlang;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.celt.translate.business.models.Lang;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SelectLang extends MvpView {

//    void setLangs(LangsResponse langs);

    void setLangs(List<Lang> langs);
}
