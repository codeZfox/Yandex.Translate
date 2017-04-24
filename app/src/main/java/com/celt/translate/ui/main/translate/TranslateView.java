package com.celt.translate.ui.main.translate;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.celt.translate.business.models.Lang;
import com.celt.translate.data.models.dictionary.Def;
import com.celt.translate.ui.base.AddToEndSingleByTagStateStrategy;

import java.util.List;
import java.util.Queue;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface TranslateView extends MvpView {

    void setLangSource(Lang lang);

    void setLangTarget(Lang lang);

//    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "showTranslate")
//    void showTranslate(List<String> list);

    //    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "showTranslate")
    void showTranslate(String text);

    void setSourceText(String textTarget);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "DictionaryInfo")
    void showDictionaryInfo(List<Def> response);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "DictionaryInfo")
    void hideDictionaryInfo();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openSelectLangFromScreen(Lang langFrom, Queue<Lang> list);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openSelectLangToScreen(Lang langFrom, Queue<Lang> list);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "AnimationPlayTextSource")
    void showAnimationPlayTextSource();

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "AnimationPlayTextSource")
    void hideAnimationPlayTextSource(boolean isPlay);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "AnimationPlayTextTarget")
    void showAnimationPlayTextTarget();

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "AnimationPlayTextTarget")
    void hideAnimationPlayTextTarget(boolean isPlay);

    void enablePlayBtnTextSource(boolean isPlay);

    void enablePlayBtnTextTarget(boolean isPlay);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(String message);

    void showBookMaker(boolean isFavorite);

    void showBtnSource(boolean isShow);

    void showBtnTarget(boolean isShow);
}
