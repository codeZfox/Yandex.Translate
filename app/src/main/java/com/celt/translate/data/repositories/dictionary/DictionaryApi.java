package com.celt.translate.data.repositories.dictionary;

import com.celt.translate.data.models.LookupResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DictionaryApi {

    String URL = "https://dictionary.yandex.net/api/v1/dicservice.json/";

    String KEY = "dict.1.1.20170421T230015Z.a03d5c18ac8b7592.4129d898f867837a56fe9c475066c3c880bce91f";

    @GET("lookup")
    Single<LookupResponse> lookup(@Query("text") String text, @Query("lang") String lang, @Query("ui") String ui, @Query("flags") int flags);
}
