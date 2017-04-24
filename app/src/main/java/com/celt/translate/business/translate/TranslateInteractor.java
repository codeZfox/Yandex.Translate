package com.celt.translate.business.translate;

import com.celt.translate.business.models.Lang;
import com.celt.translate.business.models.Translate;
import com.celt.translate.data.models.LookupResponse;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

public interface TranslateInteractor {

    Observable<List<Lang>> getLangs(String ui);

    Observable<Translate> translate(String text, Lang langFrom, Lang langTo, boolean save);

    Observable<Translate> getHistory();

    Observable<Translate> getFavorites();

    Completable mark(Translate item);

    Completable mark(Translate item, boolean mark);

    Single<LookupResponse> lookup(String text, Lang langFrom, Lang langTo);

    Completable removeFromHistory(Translate item);

    Completable setLangTarget(Lang lang);

    Single<Lang> getLangTarget();

    Completable setLangSource(Lang lang);

    Single<Lang> getLangSource();

    Completable setLastTranslation(Translate value);

    Maybe<Translate> getLastTranslation();
}
