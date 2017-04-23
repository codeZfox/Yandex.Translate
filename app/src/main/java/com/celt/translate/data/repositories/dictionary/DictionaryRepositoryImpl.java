package com.celt.translate.data.repositories.dictionary;

import com.celt.translate.data.models.LookupResponse;
import com.celt.translate.data.repositories.retrofit.API;
import io.reactivex.Single;

public class DictionaryRepositoryImpl implements DictionaryRepository {

    private DictionaryApi api = API.createDictionaryApi();
    /**
     * Опции поиска (битовая маска флагов).
     * Возможные значения:
     * FAMILY = 0x0001 - применить семейный фильтр;
     * SHORT_POS = 0x0002 - отображать названия частей речи в краткой форме;
     * MORPHO = 0x0004 - включает поиск по форме слова;
     * POS_FILTER = 0x0008 - включает фильтр, требующий соответствия частей речи искомого слова и перевода.
     */
    private static final int flags = 0x0002;

    @Override
    public Single<LookupResponse> lookup(String text, String lang, String ui) {
        return api.lookup(text, lang, ui, flags);
    }
}
