package com.celt.translate.data.repositories.database;

import com.celt.translate.business.models.Translate;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

public interface DatabaseRepository {

     Observable<Translate> getHistory();

     Observable<Translate> getFavorites();

     Single<Translate> saveTranslate(Translate translate);

     Single<List<Translate>> findTranslate(String text, String langSource, String langTarget);

     void updateTranslate(Translate translate);

     Completable mark(Translate item);
}