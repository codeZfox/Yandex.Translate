package com.celt.translate.data.repositories.translate;

import com.celt.translate.data.models.responses.LangsResponse;
import com.celt.translate.data.models.responses.TranslateResponse;
import com.celt.translate.data.repositories.retrofit.API;
import io.reactivex.Single;

public class TranslateRepositoryImpl implements TranslateRepository{

    private TranslateApi api = API.createTranslateApi();

    public Single<LangsResponse> getLangs(String ui) {
        return api.getLangs(ui);
    }

    @Override
    public Single<TranslateResponse> translate(String text, String lang) {
        return api.translate(text, lang);
    }
}