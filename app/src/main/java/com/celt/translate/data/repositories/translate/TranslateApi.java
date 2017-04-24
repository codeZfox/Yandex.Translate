package com.celt.translate.data.repositories.translate;

import com.celt.translate.data.models.LangsResponse;
import com.celt.translate.data.models.TranslateResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TranslateApi {

    String URL = "https://translate.yandex.net/api/v1.5/tr.json/";

    String KEY = "trnsl.1.1.20170420T191525Z.4e6ef0c524c0f237.e636b3e56e58f1ae3ed2563149a27fad43f17ac5";

    @GET("getLangs")
    Observable<LangsResponse> getLangs(@Query("ui") String ui);

    @GET("translate")
    Observable<TranslateResponse> translate(@Query("text") String text, @Query("lang") String lang);

}

