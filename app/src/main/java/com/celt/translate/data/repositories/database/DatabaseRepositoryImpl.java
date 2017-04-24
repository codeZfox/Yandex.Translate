package com.celt.translate.data.repositories.database;

import android.content.Context;
import com.celt.translate.business.models.OrmaDatabase;
import com.celt.translate.business.models.Translate;
import io.reactivex.Completable;
import io.reactivex.Observable;

import java.util.Date;
import java.util.List;

public class DatabaseRepositoryImpl implements DatabaseRepository {

    private final OrmaDatabase orma;

    public DatabaseRepositoryImpl(Context context) {
        orma = OrmaDatabase.builder(context)
                .name("history")
                .build();
    }

    public Observable<Translate> getHistory() {
        return orma.selectFromTranslate()
                .isHistoryEq(true)
                .orderByDateHistoryDesc()
                .executeAsObservable();
    }

    public Observable<Translate> getFavorites() {
        return orma.selectFromTranslate()
                .isFavoriteEq(true)
                .orderByDateFavoriteDesc()
                .executeAsObservable();
    }

    public Observable<Translate> saveTranslate(Translate translate) {
        return orma.selectFromTranslate()
                .idEq(orma.insertIntoTranslate(translate))
                .executeAsObservable();
    }

    @Override
    public void updateTranslate(Translate translate) {
        orma.updateTranslate()
                .idEq(translate.id)
                .dateHistory(new Date())
                .execute();
    }

    public Observable<List<Translate>> findTranslate(String text, String langSource, String langTarget) {
        return Observable.just(orma.selectFromTranslate()
                .sourceEq(text)
                .targetLangEq(langTarget)
                .sourceLangEq(langSource)
                .orderByIsHistoryDesc()
                .toList());

    }

    @Override
    public Completable mark(Translate item) {

        return orma.updateTranslate()
                .idEq(item.id)
                .isFavorite(!item.isFavorite)
                .dateFavorite(new Date())
                .executeAsSingle()
                .toCompletable();
    }
}