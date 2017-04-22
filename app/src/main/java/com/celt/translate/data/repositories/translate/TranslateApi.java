package com.celt.translate.data.repositories.translate;

import com.celt.translate.data.models.LangsResponse;
import com.celt.translate.data.models.TranslateResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TranslateApi {

    @GET("getLangs")
    Single<LangsResponse> getLangs(@Query("ui") String ui);

    @GET("translate")
    Single<TranslateResponse> translate(@Query("text") String text, @Query("lang") String lang);

}

