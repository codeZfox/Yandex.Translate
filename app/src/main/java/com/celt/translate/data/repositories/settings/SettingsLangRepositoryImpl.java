package com.celt.translate.data.repositories.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.celt.translate.business.models.Lang;
import com.celt.translate.business.models.Translate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.*;

public class SettingsLangRepositoryImpl implements SettingsLangRepository {

    private static final String PREFS_NAME = "PREFS_NAME";
    private static final String LANG_TARGET = "LANG_TARGET";
    private static final String LANG_SOURCE = "LANG_SOURCE";
    private static final String TRANSLATE = "TRANSLATE";
    private static final String QUEUE_SOURCE = "QUEUE_SOURCE";
    private static final String QUEUE_TARGET = "QUEUE_TARGET";

    private final SharedPreferences sharedPreferences;

    public String ui;
    private Lang localLang;
    private String englishDisplayName;

    private static final int QUEUE_SIZE = 3;
    private Queue<Lang> queueSource;
    private Queue<Lang> queueTarget;

    private static final String[] uiArray = {"ru", "en", "tr", "uk"};


    public SettingsLangRepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        Locale localUi = getCurrentLanguage(context);
        localLang = new Lang(localUi.getLanguage(), toString(localUi));
        ui = Arrays.asList(uiArray).contains(localLang.getCode()) ? localLang.getCode() : "en";
        englishDisplayName = toString(Locale.ENGLISH);

        queueSource = getQueueSource();
        queueTarget = getQueueTarget();
    }

    private String toString(Locale localUi) {
        return localUi.getDisplayLanguage().substring(0, 1).toUpperCase() + localUi.getDisplayLanguage().substring(1);
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
        offerQueue(queueTarget, lang);
        saveQueue(queueTarget, QUEUE_TARGET);
        return setLang(lang, LANG_TARGET);
    }

    private void offerQueue(Queue<Lang> queue, Lang lang) {
        if (!queue.contains(lang)) {
            if (queue.size() == QUEUE_SIZE) {
                queue.poll();
            }
            queue.offer(lang);
        } else {
            queue.remove(lang);
            queue.offer(lang);
        }
    }

    @Override
    public Single<Lang> getLangTargetSingle() {
        return Single.just(getLangTarget());
    }

    @Override
    public Completable setLangSource(Lang lang) {
        offerQueue(queueSource, lang);
        saveQueue(queueSource, QUEUE_SOURCE);
        return setLang(lang, LANG_SOURCE);
    }

    private void saveQueue(Queue<Lang> queueSource, String key) {
        sharedPreferences.edit().putString(key, new Gson().toJson(queueSource)).apply();
    }

    public Single<Queue<Lang>> getQueueSourceSingle() {
        return Single.just(getQueueSource());
    }

    public Single<Queue<Lang>> getQueueTargetSingle() {
        return Single.just(getQueueTarget());
    }

    private Queue<Lang> getQueueSource() {
        if (queueSource == null) {

            Queue<Lang> queue = getQueue(QUEUE_SOURCE);

            if (queue != null) {
                this.queueSource = queue;
            } else {
                queue = new ArrayDeque<>();
                queue.add(getLangSource());
                this.queueSource = queue;
            }
        }

        return this.queueSource;
    }

    private Queue<Lang> getQueueTarget() {
        if (queueTarget == null) {

            Queue<Lang> queue = getQueue(QUEUE_TARGET);

            if (queue != null) {
                this.queueTarget = queue;
            } else {
                queue = new ArrayDeque<>();
                queue.add(getLangTarget());
                this.queueTarget = queue;
            }
        }

        return this.queueTarget;
    }

    private Queue<Lang> getQueue(String key) {
        String json = sharedPreferences.getString(key, "");
        if (json.isEmpty()) {
            return null;
        } else {
            return new Gson().fromJson(json, new TypeToken<Queue<Lang>>() {
            }.getType());
        }
    }

    @Override
    public Single<Lang> getLangSourceSingle() {
        return Single.just(getLangSource());
    }

    private Lang getLangSource() {
        String string = sharedPreferences.getString(LANG_SOURCE, "");
        if (string.isEmpty()) {
            return localLang;
        } else {
            return new Gson().fromJson(string, Lang.class);
        }
    }

    private Lang getLangTarget() {
        String string = sharedPreferences.getString(LANG_TARGET, "");
        if (string.isEmpty()) {
            return new Lang("en", englishDisplayName);
        } else {
            return new Gson().fromJson(string, Lang.class);
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
