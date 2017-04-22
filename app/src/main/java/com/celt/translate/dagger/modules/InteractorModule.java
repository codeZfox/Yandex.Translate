package com.celt.translate.dagger.modules;

import android.support.annotation.NonNull;
import com.celt.translate.business.translate.TranslateInteractor;
import com.celt.translate.business.translate.TranslateInteractorImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;


@Module
public class InteractorModule {

    @Provides
    @NonNull
    @Singleton
    TranslateInteractor provideReportsRepository() {
        return new TranslateInteractorImpl();
    }

}

