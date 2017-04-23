package com.celt.translate.dagger.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import com.celt.translate.data.repositories.database.DatabaseRepository;
import com.celt.translate.data.repositories.database.DatabaseRepositoryImpl;
import com.celt.translate.data.repositories.dictionary.DictionaryRepository;
import com.celt.translate.data.repositories.dictionary.DictionaryRepositoryImpl;
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

    @Provides
    @NonNull
    @Singleton
    DictionaryRepository provideDictionaryRepository() {
        return new DictionaryRepositoryImpl();
    }

    @Provides
    @NonNull
    @Singleton
    DatabaseRepository provideDatabaseRepository(Context context) {
        return new DatabaseRepositoryImpl(context);
    }
}

