package com.celt.translate.dagger.modules;

import android.support.annotation.NonNull;
import com.celt.translate.data.repositories.translate.TranslateRepository;
import com.celt.translate.data.repositories.translate.TranslateRepositoryImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RepositoriesModule {

    @Provides
    @NonNull
    @Singleton
    TranslateRepository provideReportsRepository() {
        return new TranslateRepositoryImpl();
    }
}

