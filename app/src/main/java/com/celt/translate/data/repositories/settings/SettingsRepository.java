package com.celt.translate.data.repositories.settings;

import com.celt.translate.business.models.Lang;
import com.celt.translate.business.models.Translate;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface SettingsRepository {

    Completable setLangTarget(Lang lang);

    Single<Lang> getLangTarget();

    Completable setLangSource(Lang lang);

    Single<Lang> getLangSource();

    Completable setLastTranslation(Translate value);

    Maybe<Translate> getLastTranslation();

    String getUI();
}
