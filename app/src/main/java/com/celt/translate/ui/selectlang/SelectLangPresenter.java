package com.celt.translate.ui.selectlang;

import android.content.Context;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.celt.translate.business.translate.TranslateInteractor;
import com.celt.translate.business.translate.TranslateInteractorImpl;
import com.celt.translate.business.models.Lang;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class SelectLangPresenter extends MvpPresenter<SelectLang> {

    private TranslateInteractor interactor = new TranslateInteractorImpl();
    private List<Lang> langs =  new ArrayList<>();

    public SelectLangPresenter(Context context) {

//        getViewState().setLangs(langs);

        interactor.getLangs("ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(langs -> {
                    this.langs = langs;
                    getViewState().setLangs(this.langs);
                }, e -> {

                });
    }

}
