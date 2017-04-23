package com.celt.translate.ui.main.translate;

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

    private Lang langSource;
    private Lang langTarget;

    private String textSource = "";
    private String textTarget = "";

    private Handler handlerForSearch = new Handler();

    private Vocalizer vocalizer;

    public TranslatePresenter() {
        Components.getAppComponent().inject(this);

        langSource = new Lang("ru", "Русский");
        langTarget = new Lang("en", "Английский");

        getViewState().setLangSource(langSource);
        getViewState().setLangTarget(langTarget);
    }

    public void translate(String text) {
        getViewState().hideDictionaryInfo();
        this.textSource = text;
        if (!text.trim().isEmpty()) {
            handlerForSearch.removeCallbacksAndMessages(null);
            handlerForSearch.postDelayed(() -> translateText(text, langSource, langTarget), 400L);
        } else {
            getViewState().showTranslate("");
        }
    }

    public void playText() {
        resetVocalizer();
        if (!textSource.isEmpty()) {
            vocalizer = Vocalizer.createVocalizer(Vocalizer.Language.RUSSIAN, textSource, false, Vocalizer.Voice.JANE);
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
        translate(textSource);
    }

    private void translateText(String text, Lang langFrom, Lang langTo) {
        interactor.translate(text, langFrom, langTo)
                .doOnSuccess(translate -> {
                    interactor.lookup(text, langFrom, langTo, "ru")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                getViewState().showDictionaryInfo(response.def);
                            }, Throwable::printStackTrace);
                    ;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    textTarget = response.translation;
                    getViewState().showTranslate(response.translation);
                }, Throwable::printStackTrace);
    }

    public void clickTextViewLangFrom() {
        resetVocalizer();
        getViewState().openSelectLangFromScreen(langSource);
    }

    public void clickTextViewLangTo() {
        resetVocalizer();
        getViewState().openSelectLangToScreen(langTarget);
    }

    public void setLangSource(Lang lang) {
        this.langSource = lang;
        getViewState().setLangSource(this.langSource);
        translate();
    }

    public void setLangTarget(Lang lang) {
        this.langTarget = lang;
        getViewState().setLangTarget(this.langTarget);
        translate();
    }

    public void swapLang() {
        Lang temp = langTarget;
        langTarget = langSource;
        langSource = temp;
        getViewState().setSourceText(textTarget);
//        translate(textTarget);
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

    public void setTextSource(String item) {
        Lang temp = langTarget;
        langTarget = langSource;
        langSource = temp;
        getViewState().setLangSource(langSource);
        getViewState().setLangTarget(langTarget);
        getViewState().setSourceText(item);
    }
}
