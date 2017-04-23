package com.celt.translate.ui.main.translate;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.celt.translate.business.models.Lang;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface TranslateView extends MvpView {

    void setLangFrom(Lang lang);

    void setLangTo(Lang lang);

//    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "showTranslate")
//    void showTranslate(List<String> list);

//    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "showTranslate")
    void showTranslate(String text);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openSelectLangFromScreen(Lang langFrom);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openSelectLangToScreen(Lang langFrom);

    void showAnimationPlayText(boolean isPlay);
}
