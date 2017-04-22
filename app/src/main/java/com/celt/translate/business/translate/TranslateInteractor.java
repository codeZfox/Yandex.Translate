package com.celt.translate.business.translate;

import com.celt.translate.business.models.Lang;
import io.reactivex.Single;

import java.util.List;

public interface TranslateInteractor {

    Single<List<Lang>> getLangs(String ui);

}
