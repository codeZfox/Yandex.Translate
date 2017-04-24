package com.celt.translate.ui.selectlang;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.celt.translate.business.models.Lang;
import com.celt.translate.business.translate.TranslateInteractor;
import com.celt.translate.dagger.Components;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class SelectLangPresenter extends MvpPresenter<SelectLangView> {

    @Inject
    public TranslateInteractor interactor;

    private List<Lang> langs = new ArrayList<>();

    public SelectLangPresenter(Lang lang) {
        Components.getAppComponent().inject(this);

        getViewState().setCurrantLang(lang);

        interactor.getLangs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(langs -> {
                    this.langs = langs;
                    getViewState().setLangs(this.langs);
                }, Throwable::printStackTrace);
    }
}
