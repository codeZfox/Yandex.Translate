package com.celt.translate.ui.main.localdata;

import android.os.Handler;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.celt.translate.R;
import com.celt.translate.business.models.Translate;
import com.celt.translate.business.translate.TranslateInteractor;
import com.celt.translate.dagger.Components;
import io.reactivex.Observable;
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
    private TypeLocalFragment type;

    private Handler handlerForSearch = new Handler();

    public HistoryPresenter(TypeLocalFragment type) {
        this.type = type;
        Components.getAppComponent().inject(this);

        getViewState().setItems(items);
        getViewState().showBtnClear(false);

        switch (type) {
            case HISTORY: {
                getViewState().setSearchFiledHintText(R.string.search_in_history);
                break;
            }
            case FAVORITES: {
                getViewState().setSearchFiledHintText(R.string.search_in_fav);
                break;
            }
        }

    }


    public void update() {
        items.clear();
        getViewState().updateItems();
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
                    getViewState().updateItems();
                }, Throwable::printStackTrace);
    }

    private void getFavorites() {
        interactor.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    items.add(response);
                    getViewState().showPlaceHolder(type, items.isEmpty());
                    getViewState().updateItems();
                }, Throwable::printStackTrace);
    }

    public void mark(Translate item) {
        interactor.mark(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    item.isFavorite = !item.isFavorite;
                    getViewState().updateItems();
                }, Throwable::printStackTrace);
    }

    public void longClick(Translate item) {
        getViewState().showDeleteDialog(item, true);
    }

    public void cancelDeleteDialog() {
        getViewState().showDeleteDialog(null, false);
    }

    public void delete(Translate item) {
        switch (type) {
            case HISTORY: {
                interactor.removeFromHistory(item)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            items.remove(item);
                            getViewState().updateItems();
                        }, Throwable::printStackTrace);
                break;
            }
            case FAVORITES: {
                interactor.mark(item, false)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            items.remove(item);
                            getViewState().updateItems();
                        }, Throwable::printStackTrace);
                break;
            }
        }
    }

    public void search(String text) {

        getViewState().showBtnClear(!text.isEmpty());

        if (text.isEmpty()) {
            getViewState().setItems(items);
            getViewState().updateItems();
            getViewState().showPlaceHolder(type, items.isEmpty());
            return;
        }

        handlerForSearch.removeCallbacksAndMessages(null);
        handlerForSearch.postDelayed(() -> {



            String search = text.toLowerCase();
            Observable.fromIterable(items)
                    .filter(translate -> translate.source.toLowerCase().contains(search) || translate.translation.toLowerCase().contains(search))
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        getViewState().showPlaceHolder(type, list.isEmpty());
                        getViewState().setItems(list);
                        getViewState().updateItems();
                    }, Throwable::printStackTrace);

        }, 150L);


    }
}
