package com.celt.translate.ui.selectlang;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.celt.translate.business.models.Lang;
import com.celt.translate.business.translate.TranslateInteractor;
import com.celt.translate.dagger.Components;
import com.celt.translate.ui.selectlang.adapter.LangSelectable;
import com.celt.translate.ui.selectlang.adapter.LangTitle;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@InjectViewState
public class SelectLangPresenter extends MvpPresenter<SelectLangView> {

    @Inject
    public TranslateInteractor interactor;

    private List<LangSelectable> langs = new ArrayList<>();

    public SelectLangPresenter(ArrayList<Lang> list) {
        Components.getAppComponent().inject(this);

        Collections.reverse(list);
        this.langs.add(new LangTitle("НЕДАВНО ИСПОЛЬЗОВАННЫЕ"));
        this.langs.addAll(list);

        interactor.getLangs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(langs -> {
                    this.langs.add(new LangTitle("ВСЕ ЯЗЫКИ"));
                    this.langs.addAll(langs);
                    getViewState().setLangs(this.langs);
                }, Throwable::printStackTrace);
    }
}
