package com.celt.translate.business.translate;

import com.celt.translate.business.models.Lang;
import com.celt.translate.data.models.TranslateResponse;
import io.reactivex.Single;

import java.util.List;

public interface TranslateInteractor {

    Single<List<Lang>> getLangs(String ui);

    Single<TranslateResponse> translate(String text, Lang langFrom, Lang langTo);

}
