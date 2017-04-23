package com.celt.translate.data.repositories.retrofit;

import com.celt.translate.data.repositories.dictionary.DictionaryApi;
import com.celt.translate.data.repositories.translate.TranslateApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class API {


    public static TranslateApi createTranslateApi() {

        return new Retrofit.Builder()
                .baseUrl(TranslateApi.URL)
                .client(createHttpClient(TranslateApi.KEY))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TranslateApi.class);

    }

    public static DictionaryApi createDictionaryApi() {

        return new Retrofit.Builder()
                .baseUrl(DictionaryApi.URL)
                .client(createHttpClient(DictionaryApi.KEY))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DictionaryApi.class);

    }

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
