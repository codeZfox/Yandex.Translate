package com.celt.translate.data.repositories.translate;

import com.celt.translate.data.models.LangsResponse;
import com.celt.translate.data.models.TranslateResponse;
import io.reactivex.Observable;

public interface TranslateRepository {

    Observable<LangsResponse> getLangs(String ui);

    Observable<TranslateResponse> translate(String text, String lang);

}
