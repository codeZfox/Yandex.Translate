package com.celt.translate.data.repositories.database;

import com.celt.translate.business.models.Translate;
import io.reactivex.Completable;
import io.reactivex.Observable;

import java.util.List;

public interface DatabaseRepository {

     Observable<Translate> getHistory();

     Observable<Translate> getFavorites();

     Observable<Translate> saveTranslate(Translate translate);

     Observable<List<Translate>> findTranslate(String text, String langSource, String langTarget);

     void updateTranslate(Translate translate);

     Completable mark(Translate item);
}