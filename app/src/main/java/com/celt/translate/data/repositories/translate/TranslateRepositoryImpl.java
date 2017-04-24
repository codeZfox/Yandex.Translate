package com.celt.translate.data.repositories.translate;

import com.celt.translate.data.models.LangsResponse;
import com.celt.translate.data.models.TranslateResponse;
import com.celt.translate.data.repositories.retrofit.API;
import io.reactivex.Observable;

public class TranslateRepositoryImpl implements TranslateRepository{

    private TranslateApi api = API.createTranslateApi();

    public Observable<LangsResponse> getLangs(String ui) {
        return api.getLangs(ui);
    }

    @Override
    public Observable<TranslateResponse> translate(String text, String lang) {
        return api.translate(text, lang);
    }
}