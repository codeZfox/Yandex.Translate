package com.celt.translate.business.translate;

import com.celt.translate.business.models.Lang;
import com.celt.translate.business.models.Translate;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

public interface TranslateInteractor {

    Single<List<Lang>> getLangs(String ui);

    Single<Translate> translate(String text, Lang langFrom, Lang langTo);

    Observable<Translate> getHistory();

    Observable<Translate> getFavorites();

    Completable mark(Translate item);
}
