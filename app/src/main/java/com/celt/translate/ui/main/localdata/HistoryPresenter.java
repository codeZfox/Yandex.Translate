package com.celt.translate.ui.main.localdata;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.celt.translate.business.translate.TranslateInteractor;
import com.celt.translate.dagger.Components;
import com.celt.translate.business.models.Translate;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class HistoryPresenter extends MvpPresenter<HistoryView> {

    @Inject
    public TranslateInteractor interactor;

    private List<Translate> items = new ArrayList<>();
    TypeLocalFragment type;

    public HistoryPresenter(TypeLocalFragment type) {
        this.type = type;
        Components.getAppComponent().inject(this);

        getViewState().setHistory(items);

        update();
    }


    public void update() {
        items.clear();
        getViewState().updateHistory();
        getViewState().showPlaceHolder(type, items.isEmpty());

        switch (type) {
            case HISTORY: {
                getHistory();
                break;
            }
            case FAVORITES: {
                getFavorites();
                break;
            }
        }
    }

    private void getHistory() {
        interactor.getHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    items.add(response);
                    getViewState().showPlaceHolder(type, items.isEmpty());
                    getViewState().updateHistory();
                }, Throwable::printStackTrace);
    }

    private void getFavorites() {
        interactor.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    items.add(response);
                    getViewState().showPlaceHolder(type, items.isEmpty());
                    getViewState().updateHistory();
                }, Throwable::printStackTrace);
    }

    public void mark(Translate item) {
        interactor.mark(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    item.isFavorite = !item.isFavorite;
                    getViewState().updateHistory();
                }, Throwable::printStackTrace);
    }

    public void delete(Translate item) {

    }
}
