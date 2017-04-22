package com.celt.translate.data.repositories.retrofit;

import com.celt.translate.data.repositories.translate.TranslateApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class API {

    private static final String URL_TRNSL = "https://translate.yandex.net/api/v1.5/tr.json/";

    private static final String KEY_TRNSL = "trnsl.1.1.20170420T191525Z.4e6ef0c524c0f237.e636b3e56e58f1ae3ed2563149a27fad43f17ac5";

    public static TranslateApi createTranslateApi() {

        return new Retrofit.Builder()
                .baseUrl(URL_TRNSL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createHttpClient(KEY_TRNSL))
                .build()
                .create(TranslateApi.class);

    }
//
//    public Retrofit build() {
//
//        return new Retrofit.Builder()
//                .baseUrl(URL_TRNSL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(createHttpClient(KEY_TRNSL))
//                .build();
//
//    }

    private static OkHttpClient createHttpClient(String key) {
        return new OkHttpClient.Builder()
                .addInterceptor(new QueryInterceptor(key))
                .addInterceptor(createHttpLoggingInterceptor())
                .build();
    }

    private static HttpLoggingInterceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
