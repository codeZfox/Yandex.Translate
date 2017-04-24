package com.celt.translate.ui.main.translate;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.celt.translate.R;
import com.celt.translate.business.models.Lang;
import com.celt.translate.business.models.Translate;
import com.celt.translate.business.translate.TranslateInteractor;
import com.celt.translate.dagger.Components;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;

import javax.inject.Inject;

import static android.content.Context.CLIPBOARD_SERVICE;

@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {

    @Inject
    public TranslateInteractor interactor;

    private Translate translate;

    private Lang langSource;
    private Lang langTarget;

    private boolean isPlayTextTarget;
    private boolean isPlayTextSource;

    private String textSource = "";
    private String textTarget = "";

    private Handler handlerForSearch = new Handler();
    private Vocalizer vocalizer;

    public TranslatePresenter() {
        Components.getAppComponent().inject(this);

        interactor.getLangSource()
                .doOnSuccess(lang -> {
                    langSource = lang;
                })
                .flatMap(lang -> interactor.getLangTarget())
                .doOnSuccess(lang -> {
                    langTarget = lang;
                })
                .doOnSuccess( lang -> interactor.getLastTranslation()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(translate1 -> {
                            this.translate = translate1;
                            this.textSource = translate.source;
                            this.textTarget = translate.translation;
                            getViewState().setSourceText(textSource);
                        },Throwable::printStackTrace))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translate -> {
                    getViewState().setLangSource(langSource);
                    getViewState().setLangTarget(langTarget);
                    checkLangPlayText();
                }, Throwable::printStackTrace);


        getViewState().showBtnSource(false);
        getViewState().showBtnTarget(false);
    }

    public void translate(String text, boolean done) {
        if (!done) {
            getViewState().hideDictionaryInfo();
        }
        this.textSource = text;
        getViewState().showBtnSource(!text.trim().isEmpty());
        if (!text.trim().isEmpty()) {
            handlerForSearch.removeCallbacksAndMessages(null);
            handlerForSearch.postDelayed(() -> translateText(text, langSource, langTarget, done), 500L);
        } else {
            getViewState().hideDictionaryInfo();
            getViewState().showBtnTarget(false);
            getViewState().showTranslate("");
        }
    }

    public void playSourceText() {
        resetVocalizer();
        if (isPlayTextSource) {
            if (!textSource.isEmpty()) {
                getViewState().showAnimationPlayTextSource();
                playText(langSource.getCode(), textSource);
            }
        } else {
            getViewState().showMessage("Озвучивание на этом языке пока нет");
        }
    }

    public void playTargetText() {
        resetVocalizer();
        if (isPlayTextTarget) {
            if (!textTarget.isEmpty()) {
                getViewState().showAnimationPlayTextTarget();
                playText(langTarget.getCode(), textTarget);
            }
        } else {
            getViewState().showMessage("Озвучивание на этом языке пока нет");
        }
    }

    private void playText(String lang, String text) {
        vocalizer = Vocalizer.createVocalizer(lang, text, false, Vocalizer.Voice.JANE);
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
                getViewState().hideAnimationPlayTextTarget(isPlayTextTarget);
                getViewState().hideAnimationPlayTextSource(isPlayTextSource);
            }

            @Override
            public void onVocalizerError(Vocalizer vocalizer, Error error) {
                Log.e("Vocalizer", error.getString());
                getViewState().hideAnimationPlayTextTarget(isPlayTextTarget);
                getViewState().hideAnimationPlayTextSource(isPlayTextSource);
            }
        });
        vocalizer.start();
    }

    private void translate() {
        resetVocalizer();
        translate(textSource, true);
    }

    private void translateText(String text, Lang langFrom, Lang langTo, boolean save) {
        interactor.translate(text, langFrom, langTo, save)
                .doOnNext(translate -> {

                    this.translate = translate;
                    interactor.lookup(text, langFrom, langTo)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                getViewState().showDictionaryInfo(response.def);
                            }, Throwable::printStackTrace);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translate1 -> {
                    getViewState().showBtnTarget(true);
                    textTarget = translate1.translation;
                    getViewState().showBookMaker(translate1.isFavorite);
                    getViewState().showTranslate(translate1.translation);
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

    private void saveLang() {
        interactor.setLangSource(langSource)
                .doOnComplete(() -> interactor.setLangTarget(langTarget))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void setLangSource(Lang lang) {
        resetVocalizer();
        this.langSource = lang;
        isPlayTextSource = checkPlayLang(lang);
        getViewState().enablePlayBtnTextSource(isPlayTextSource);
        getViewState().setLangSource(this.langSource);
        saveLang();
    }

    public void setLangTarget(Lang lang) {
        this.langTarget = lang;
        isPlayTextTarget = checkPlayLang(lang);
        getViewState().enablePlayBtnTextTarget(isPlayTextTarget);
        getViewState().setLangTarget(this.langTarget);
        translate();
        saveLang();
    }

    private boolean checkPlayLang(Lang lang) {
        String code = lang.getCode();
        return code.equals(Vocalizer.Language.ENGLISH)
                || code.equals(Vocalizer.Language.RUSSIAN)
                || code.equals(Vocalizer.Language.TURKISH)
                || code.equals(Vocalizer.Language.UKRAINIAN);
    }

    public void swapLang() {
        resetVocalizer();
        Lang temp = langTarget;
        langTarget = langSource;
        langSource = temp;
        checkLangPlayText();
        getViewState().setSourceText(textTarget);
//        translate(textTarget);
    }

    private void checkLangPlayText() {
        isPlayTextSource = checkPlayLang(langSource);
        getViewState().enablePlayBtnTextSource(isPlayTextSource);
        isPlayTextTarget = checkPlayLang(langTarget);
        getViewState().enablePlayBtnTextTarget(isPlayTextTarget);
    }

    private void resetVocalizer() {
        getViewState().hideAnimationPlayTextTarget(isPlayTextTarget);
        getViewState().hideAnimationPlayTextSource(isPlayTextSource);

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
        resetVocalizer();
        Lang temp = langTarget;
        langTarget = langSource;
        langSource = temp;
        checkLangPlayText();
        getViewState().setLangSource(langSource);
        getViewState().setLangTarget(langTarget);
        getViewState().setSourceText(item);
        saveLang();
    }

    public void bookmaker() {
        interactor.mark(translate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    translate.isFavorite = !translate.isFavorite;
                    getViewState().showBookMaker(translate.isFavorite);
                }, Throwable::printStackTrace);
    }

    public void update() {
        interactor.translate(textSource, langSource, langTarget, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translate1 -> {
                    this.translate = translate1;
                    getViewState().showBookMaker(translate.isFavorite);
                }, Throwable::printStackTrace);
    }

    public void copySourceText(Context context) {
        copyText(context, textTarget);
    }

    public void copyText(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
        getViewState().showMessage(context.getString(R.string.translate_copy));
    }
}
