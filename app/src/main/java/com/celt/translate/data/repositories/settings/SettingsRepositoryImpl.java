package com.celt.translate.data.repositories.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.celt.translate.business.models.Lang;
import com.celt.translate.business.models.Translate;
import com.google.gson.Gson;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.Arrays;
import java.util.Locale;

public class SettingsRepositoryImpl implements SettingsRepository {

    private static final String PREFS_NAME = "PREFS_NAME";
    private static final String LANG_TARGET = "LANG_TARGET";
    private static final String LANG_SOURCE = "LANG_SOURCE";
    private static final String TRANSLATE = "TRANSLATE";

    private final SharedPreferences sharedPreferences;

    public String ui;
    private Lang localLang;

    private static final String[] uiArray = {"ru", "en", "tr", "uk"};

    public SettingsRepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        Locale localUi = getCurrentLanguage(context);
        localLang = new Lang(localUi.getLanguage(), localUi.getDisplayLanguage().substring(0, 1).toUpperCase() + localUi.getDisplayLanguage().substring(1));
        ui = Arrays.asList(uiArray).contains(localLang.getCode()) ? localLang.getCode() : "en";
    }


    private Locale getCurrentLanguage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    private Completable setLang(Lang value, String key) {
        sharedPreferences.edit().putString(key, new Gson().toJson(value)).apply();
        return Completable.complete();
    }

    public Completable setLangTarget(Lang lang) {
        return setLang(lang, LANG_TARGET);
    }

    @Override
    public Single<Lang> getLangTarget() {
        String string = sharedPreferences.getString(LANG_TARGET, "");
        if (string.isEmpty()) {
            return Single.just(new Lang("en", "English"));
        } else {
            return Single.just(new Gson().fromJson(string, Lang.class));
        }
    }

    @Override
    public Completable setLangSource(Lang lang) {
        return setLang(lang, LANG_SOURCE);
    }

    @Override
    public Single<Lang> getLangSource() {
        String string = sharedPreferences.getString(LANG_SOURCE, "");
        if (string.isEmpty()) {
            return Single.just(localLang);
        } else {
            return Single.just(new Gson().fromJson(string, Lang.class));
        }
    }

    public Completable setLastTranslation(Translate value) {
        return setTranslate(value, TRANSLATE);
    }

    @Override
    public Maybe<Translate> getLastTranslation() {
        return getTranslate(TRANSLATE);
    }

    private Maybe<Translate> getTranslate(String key) {
        String string = sharedPreferences.getString(key, "");
        return string.isEmpty() ? Maybe.empty() : Maybe.just(new Gson().fromJson(string, Translate.class));
    }

    private Completable setTranslate(Translate value, String key) {
        sharedPreferences.edit().putString(key, new Gson().toJson(value)).apply();
        return Completable.complete();
    }

    @Override
    public String getUI() {
        return ui;
    }
}
