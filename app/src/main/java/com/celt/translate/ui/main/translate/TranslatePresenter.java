package com.celt.translate.ui.main.translate;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.celt.translate.business.models.Lang;
import com.celt.translate.business.translate.TranslateInteractor;
import com.celt.translate.dagger.Components;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;

import javax.inject.Inject;

@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {

    @Inject
    public TranslateInteractor interactor;

    private Lang langFrom;
    private Lang langTo;

    private String text = "";

    private Handler handlerForSearch = new Handler();

    private Vocalizer vocalizer;

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

    public void playText() {
        resetVocalizer();
        if (!text.isEmpty()) {
            vocalizer = Vocalizer.createVocalizer(Vocalizer.Language.RUSSIAN, text, false, Vocalizer.Voice.JANE);
            vocalizer.setListener(new VocalizerListener() {
                @Override
                public void onSynthesisBegin(Vocalizer vocalizer) {

                }

                @Override
                public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {
                    vocalizer.play();
                }

                @Override
                public void onPlayingBegin(Vocalizer vocalizer) {
                }

                @Override
                public void onPlayingDone(Vocalizer vocalizer) {
                    getViewState().showAnimationPlayText(false);
                }

                @Override
                public void onVocalizerError(Vocalizer vocalizer, Error error) {
                    Log.e("Vocalizer", error.getString());
                    getViewState().showAnimationPlayText(false);
                }
            });
            vocalizer.start();
            getViewState().showAnimationPlayText(true);


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
        resetVocalizer();
        getViewState().openSelectLangFromScreen(langFrom);
    }

    public void clickTextViewLangTo() {
        resetVocalizer();
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

    private void resetVocalizer() {
        if (vocalizer != null) {
            vocalizer.cancel();
            vocalizer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        resetVocalizer();
    }
}
