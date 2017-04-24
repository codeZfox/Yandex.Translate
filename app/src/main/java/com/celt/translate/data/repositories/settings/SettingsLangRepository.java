package com.celt.translate.data.repositories.settings;

import com.celt.translate.business.models.Lang;
import com.celt.translate.business.models.Translate;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.Queue;

public interface SettingsLangRepository {

    Completable setLangTarget(Lang lang);
    Single<Lang> getLangTargetSingle();
    Single<Queue<Lang>> getQueueTargetSingle();

    Completable setLangSource(Lang lang);
    Single<Lang> getLangSourceSingle();
    Single<Queue<Lang>> getQueueSourceSingle();


    Completable setLastTranslation(Translate value);
    Maybe<Translate> getLastTranslation();

    String getUI();
}
