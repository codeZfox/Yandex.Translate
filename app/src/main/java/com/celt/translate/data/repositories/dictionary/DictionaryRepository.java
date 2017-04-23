package com.celt.translate.data.repositories.dictionary;

import com.celt.translate.data.models.LookupResponse;
import io.reactivex.Single;

public interface DictionaryRepository {

    Single<LookupResponse> lookup(String text, String lang, String ui);

}
