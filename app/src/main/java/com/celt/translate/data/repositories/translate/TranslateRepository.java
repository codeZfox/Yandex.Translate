package com.celt.translate.data.repositories.translate;

import com.celt.translate.data.models.LangsResponse;
import io.reactivex.Single;

public interface TranslateRepository {

    Single<LangsResponse> getLangs(String ui);

}
