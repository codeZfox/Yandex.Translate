package com.celt.translate.ui.main.translate;

import android.content.Context;
import android.os.Handler;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.celt.translate.business.models.Lang;
import com.celt.translate.business.translate.TranslateInteractor;
import com.celt.translate.dagger.Components;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;

@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {

    @Inject
    public TranslateInteractor interactor;

    private Lang langFrom;
    private Lang langTo;

    private String text = "";

    private Handler handlerForSearch = new Handler();

    public TranslatePresenter(Context context) {

        Components.getAppComponent().inject(this);

        langFrom = new Lang("ru", "Русский");
        langTo = new Lang("en", "Английский");

        getViewState().setLangFrom(langFrom);
        getViewState().setLangTo(langTo);
    }

    public void translate(String text) {
        this.text = text;
        if (!text.isEmpty()) {
            handlerForSearch.removeCallbacksAndMessages(null);
            handlerForSearch.postDelayed(() -> translateText(text), 300L);
        } else {
            getViewState().showTranslate("");
        }
    }

    private void translate() {
        translate(text);
    }

    private void translateText(String text) {
        interactor.translate(text, langFrom, langTo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getViewState().showTranslate(response.getText());
                }, Throwable::printStackTrace);
    }

    public void clickTextViewLangFrom() {
        getViewState().openSelectLangFromScreen(langFrom);
    }

    public void clickTextViewLangTo() {
        getViewState().openSelectLangToScreen(langTo);
    }

    public void setLangFrom(Lang lang) {
        this.langFrom = lang;
        getViewState().setLangFrom(this.langFrom);
        translate();
    }

    public void setLangTo(Lang lang) {
        this.langTo = lang;
        getViewState().setLangTo(this.langTo);
        translate();
    }

    public void swapLang() {
        Lang temp = langTo;
        langTo = langFrom;
        langFrom = temp;
        translate();
    }
}
