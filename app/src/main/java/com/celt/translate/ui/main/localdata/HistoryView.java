package com.celt.translate.ui.main.localdata;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.celt.translate.business.models.Translate;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HistoryView extends MvpView {

    void setHistory(List<Translate> langs);

    void updateHistory();

    void showPlaceHolder(TypeLocalFragment type, boolean isShow);

    void showDeleteDialog(Translate item, boolean isShow);
}
