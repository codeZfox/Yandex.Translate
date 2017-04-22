package com.celt.translate.data.repositories.translate;

import com.celt.translate.data.models.LangsResponse;
import com.celt.translate.data.models.TranslateResponse;
import io.reactivex.Single;

public interface TranslateRepository {

    Single<LangsResponse> getLangs(String ui);

    Single<TranslateResponse> translate(String text, String lang);

}
