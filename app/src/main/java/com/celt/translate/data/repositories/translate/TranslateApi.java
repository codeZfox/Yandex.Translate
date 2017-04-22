package com.celt.translate.data.repositories.translate;

import com.celt.translate.data.models.LangsResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TranslateApi {

    @GET("getLangs")
    Single<LangsResponse> getLangs(@Query("ui") String ui);

}

